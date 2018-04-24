package com.haomins.www.newsgateway;

//import android.app.Fragment;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	static final String NEWS_STORY="NEWS_STORY";
	static final String NEWS_SOURCE="NEWS_SOURCE";
	static final String REQUEST_ARTICLES="REQUEST_ARTICLES";
	static final String RESPONSE_ARTICLES="RESPONSE_ARTICLES";

	NewsReceiver newsReceiver;
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle actionBarDrawerToggle;


	MyPageAdapter myPageAdapter;
	ViewPager myViewPage;

	ListView drawerListView;
	List<Fragment> fragments;
	ArrayList<NewsSource> newsSources = new ArrayList<>();
	ArrayAdapter<NewsSource> newsSourceArrayAdapter;

	@SuppressLint("RestrictedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = new Intent(MainActivity.this, NewsService.class);
		startService(intent);
		newsReceiver = new NewsReceiver();
		registerReceiver(newsReceiver, new IntentFilter(RESPONSE_ARTICLES));
		registerReceiver(newsReceiver, new IntentFilter(REQUEST_ARTICLES));

		drawerLayout = findViewById(R.id.main_drawerLayout);
		drawerListView = findViewById(R.id.main_drawer);
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.on, R.string.off);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		AsyncSourceLoader asl = new AsyncSourceLoader(this);
		asl.execute("");
		newsSourceArrayAdapter = new ArrayAdapter<>(this, R.layout.drawer_element, newsSources);
		drawerListView.setAdapter(newsSourceArrayAdapter);
		drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectSource(position);
			}
		});
		fragments = new ArrayList<Fragment>();

		myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
		myViewPage = findViewById(R.id.main_viewPager);
		myViewPage.setAdapter(myPageAdapter);

		if (savedInstanceState != null){
			fragments = (List<Fragment>) savedInstanceState.getSerializable("fragments");
			setTitle(savedInstanceState.getString("title"));
			myPageAdapter.notifyDataSetChanged();
			for (int i = 0; i< myPageAdapter.getCount(); i++) myPageAdapter.notifyChangeInPosition(i);
		}


	}



	@Override
	public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onPostCreate(savedInstanceState, persistentState);
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true;
		Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
		AsyncSourceLoader asl = new AsyncSourceLoader(this);
		switch (item.getItemId()){
			case R.id.menu_gaming:
				asl.execute("entertainment");
				Toast.makeText(this, "Gaming section is no longer proivided in the NEWsAPI, I change it to the entertainment category", Toast.LENGTH_LONG).show();
				return true;
			case R.id.menu_business:
				asl.execute("business");
				return true;
			case R.id.menu_sport:
				asl.execute("sports");
				return true;
			case R.id.menu_Science:
				asl.execute("science");
				return true;
			case R.id.menu_technology:
				asl.execute("technology");
				return true;
			case R.id.menu_entertainment:
				asl.execute("entertainment");
				return true;
			case R.id.menu_general:
				asl.execute("general");
				return true;
			case R.id.menu_all:
				asl.execute("");
				return true;
			default:
				asl.execute("");
				return true;
		}



		/*if (item.toString().equals("All")) {
			asl.execute("");
		}
		else {
			asl.execute(item.g.toLowerCase());
		}

		return true;*/
	}

	//TODO:broadcast
	public class NewsReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()){
				case RESPONSE_ARTICLES:
					ArrayList<Article> articles = (ArrayList<Article>) intent.getSerializableExtra("articles");
					fragments.clear();
					for (int i = 0; i<articles.size(); i++){
						fragments.add(ArticleFragment.newInstance(articles.get(i)));
						myPageAdapter.notifyChangeInPosition(i);
					}
					myPageAdapter.notifyDataSetChanged();
					myViewPage.setCurrentItem(0);
			}
		}
	}

	public void addSource(NewsSource newsSource){
		newsSources.add(newsSource);
		newsSourceArrayAdapter.notifyDataSetChanged();
	}

	private void selectSource(int position) {
		Toast.makeText(this, newsSources.get(position).getName(), Toast.LENGTH_SHORT).show();
		setTitle(newsSources.get(position).getName());
		Intent requestIntent = new Intent();
		requestIntent.setAction(MainActivity.REQUEST_ARTICLES);
		requestIntent.putExtra("source", newsSources.get(position).getId());
		sendBroadcast(requestIntent);

		drawerLayout.closeDrawer(drawerListView);
	}

	public void clearSource(){
		newsSources.clear();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(newsReceiver);
		Intent intent = new Intent(MainActivity.this, NewsService.class);
		stopService(intent);
		super.onDestroy();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("fragments", (Serializable) fragments);
		outState.putString("title",getTitle().toString());
		super.onSaveInstanceState(outState);
	}

	public class MyPageAdapter extends FragmentPagerAdapter {
		MainActivity ma;
		private long baseId = 0;
		public MyPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public long getItemId(int position) {
			return baseId+position;
		}

		public void notifyChangeInPosition(int n) {
			// shift the ID returned by getItemId outside the range of all previous fragments
			baseId += getCount() + n;
		}





}
}

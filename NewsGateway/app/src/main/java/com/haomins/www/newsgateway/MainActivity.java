package com.haomins.www.newsgateway;

//import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	static final String NEWS_STORY="NEWS_STORY";
	static final String NEWS_SOURCE="NEWS_SOURCE";
	static final String REQUEST_ARTICLES="REQUEST_ARTICLES";
	static final String RESPONSE_ARTICLES="RESPONSE_ARTICLES";

	MyPageAdapter myPageAdapter;
	ViewPager myViewPage;

	List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public class NewsReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (Objects.requireNonNull(intent.getAction())){
				case RESPONSE_ARTICLES:
					ArrayList<Article> articles = (ArrayList<Article>)intent.getSerializableExtra("articles");
					fragments.clear();
					for(int i = 0; i < articles.size(); i++){
						fragments.add(ArticleFragment.newInstance(articles.get(i)));
						myPageAdapter.notifyDataSetChanged();
						myViewPage.setCurrentItem(0);
					}
			}
		}
	}


}

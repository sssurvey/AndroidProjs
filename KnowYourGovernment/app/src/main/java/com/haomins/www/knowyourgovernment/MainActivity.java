package com.haomins.www.knowyourgovernment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import org.xml.sax.Locator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


	static MainActivity mainActivity;
	RecyclerView recyclerView;
	OfficialAdapter officialAdapter;
	List<Official> officiaList = new ArrayList<>();
	ConnectivityManager cm;
	static TextView locationTextView;
	TextView warning;
	Locator locator;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		warning = findViewById(R.id.main_warning);
		recyclerView = findViewById((R.id.main_RecyclerView));
		locationTextView = findViewById(R.id.main_Location_info);



		officialAdapter = new OfficialAdapter(officiaList,this);
		recyclerView.setAdapter(officialAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mainActivity = this;

		cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null && info.isConnectedOrConnecting()){
			locator = new Locator(this);
		} else {
			warningShow(getString(R.string.app_warning));
		}

		//if(locationTextView.getText().toString().equals())

	}

	//helpers
	public void warningShow(String update){
		warning.setVisibility(View.VISIBLE);
		warning.setText(update);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	public void warningClose(){
		warning.setVisibility(View.INVISIBLE);
	}

	public void addOfficial(Official official){
		officiaList.add(official);
		officialAdapter.notifyDataSetChanged();
	}

	public void clearOfficial(){
		officiaList.clear();
	}

	public void setLocationText(String location){
		locationTextView.setText(location);
	}

	/////////
	//location stuff
	public void setLocation(double latidude, double lontitude){
		List<Address> addresses = null;
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		try {
			addresses = geocoder.getFromLocation(latidude,lontitude, 1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		locationTextView.setText(addresses.get(0).getAddressLine(1).toString());
		AsyncDataLoader adl = new AsyncDataLoader(this);
		adl.execute(locationTextView.getText().toString());
	}
	/////////




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()){
			case R.id.main_menu_about:
				intent = new Intent(this, AboutActivity.class);
				startActivity(intent);
				return true;
			case R.id.main_menu_search:
				searchButton();
				//return false;
			default:
				return super.onOptionsItemSelected(item);
		}


	}


	//problems here
	@Override
	public void onClick(View v) {
		int pos = recyclerView.getChildAdapterPosition(v);
		Intent intent = new Intent(this, OfficialActivity.class);
		intent.putExtra("location", locationTextView.getText().toString());
		intent.putExtra("official", officiaList.get(pos));
		startActivityForResult(intent, 1);
	}


	public void searchButton(){
		LayoutInflater inflater = LayoutInflater.from(this);
		final View view = inflater.inflate(R.layout.search_dialog, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_input_hints);
		builder.setView(view);

		builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText inputTextView = view.findViewById(R.id.dialog_textedit);
				String input = inputTextView.getText().toString();

				AsyncDataLoader asyncDataLoader = new AsyncDataLoader(mainActivity);
				asyncDataLoader.execute(input);
			}
		});

		builder.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});


		AlertDialog alert = builder.create();
		alert.show();
	}
}

package com.haomins.www.scrollview;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//first a intent with messsage review
		if(getIntent().hasExtra("data")){
			Toast.makeText(this,"I was goone for "+ getIntent().getStringExtra("data"),Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "This is the first time I was opened",Toast.LENGTH_SHORT).show();
		}

	}

	public void onClick(View v){
		int gone_duration = 15; //in seconds
		Intent intentToExecute = new Intent(MainActivity.this, OnAlarmReceive.class);
		intentToExecute.putExtra("data", "I was gone for " + gone_duration + " seonds.");

		PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intentToExecute, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, gone_duration);

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC, cal.getTimeInMillis(), pi);
		Toast.makeText(MainActivity.this, "Be back in : " + gone_duration, Toast.LENGTH_SHORT).show();
		finish();
	}


}

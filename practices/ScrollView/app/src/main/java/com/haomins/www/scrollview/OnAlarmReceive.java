package com.haomins.www.scrollview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnAlarmReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
		String message = "" ;
		if(intent.hasExtra("data")){
			message = intent.getStringExtra("data");
		}
		Intent i = new Intent(context, MainActivity.class);
		i.putExtra("data", message + "\n Comming back!");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

	}
}

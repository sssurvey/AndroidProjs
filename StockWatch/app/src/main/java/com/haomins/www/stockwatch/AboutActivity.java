package com.haomins.www.stockwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by haominshi on 2/28/18.
 */

public class AboutActivity extends AppCompatActivity{

	private static final String TAG = "AboutActivity";
	static int[] easterEgg = {0,1,2};
	boolean easterSwitches = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		switchEaster();

		//Log.d(TAG, "onCreate: " + easterEgg[0]);
		Log.d(TAG, "onCreate easter: " + easterSwitches);

		}





	public void switchEaster(){

		Switch switch1 = (Switch) findViewById(R.id.switch1);
		final TextView easterText = (TextView) findViewById(R.id.easter_egg_textView);
		Switch switch2 = (Switch) findViewById(R.id.switch2);
		Switch switch3 = (Switch) findViewById(R.id.switch3);

		//final TextView easterText = (TextView) findViewById(R.id.easter_egg_textView);

		 switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					easterText.setText(R.string.after_easter_egg_text);
				} else {
					easterEgg[0] = 0; //due to time, and work, this feature is not implemented
				}
			}
		});

		switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					easterText.setText(R.string.pre_easter_egg_text);
					Log.d(TAG, "switch 2: " + easterEgg[1]);
				} else {
					easterEgg[1] = 1; //due to time, and work, this feature is not implemented
				}
			}
		});

		switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					easterText.setText(R.string.after_easter_egg_text);
					Log.d(TAG, "switch 3: " + easterEgg[2]);
				} else {
					easterEgg[2] = 2; //due to time, and work, this feature is not implemented
				}
			}
		});

	}

}

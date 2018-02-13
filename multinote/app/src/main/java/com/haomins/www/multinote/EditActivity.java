package com.haomins.www.multinote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

	

public class EditActivity extends AppCompatActivity {

	private static final String TAG = "EditActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_save:
				callSave();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void callSave() {
		Log.d(TAG, "callSave: xxxxxxxxxxx");
		//need construct json save



	}
}


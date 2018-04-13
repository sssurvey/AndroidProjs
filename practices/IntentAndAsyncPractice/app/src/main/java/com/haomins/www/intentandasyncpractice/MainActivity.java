package com.haomins.www.intentandasyncpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextView tv_b;
	TextView tv_Async;

	String hello = "Hello from main!";
	final static int REQUEST_CODE_B = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_b = findViewById(R.id.main_about);
		tv_Async = findViewById(R.id.main_async);

	}


	public void doB(View v){
		Intent new_intent = new Intent(this, BActivity.class);
		new_intent.putExtra(Intent.EXTRA_TEXT, hello);
		startActivityForResult(new_intent,REQUEST_CODE_B);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_B){
			if (resultCode == RESULT_OK){
				String text = data.getStringExtra("return from b");
				tv_b.setText(text);
			} else {
				tv_b.setText("Something went wrong");
			}
		} else {
			tv_b.setText("request code wrong" + resultCode);
		}
	}


	public void doAsync(View v){
		tv_Async.setText("Doing");
		long delay = 10;
		new MyAsyncTask(this).execute(delay);



	}

}

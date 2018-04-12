package com.haomins.www.intentandasyncpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BActivity extends AppCompatActivity {

	TextView textView_b;
	EditText editText_b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);

		editText_b = findViewById(R.id.editText_b);
		textView_b = findViewById(R.id.textView_b);

		Intent intent = getIntent();

		if (intent.hasExtra(Intent.EXTRA_TEXT)) {
			String text = intent.getStringExtra(Intent.EXTRA_TEXT);
			textView_b.setText(text);
		}
	}

	public void goBack(View v){
		Intent data = new Intent();
		data.putExtra("return from b", editText_b.getText().toString());
		setResult(RESULT_OK,data);
		finish();
	}

	@Override
	public void onBackPressed() {
		// Pressing the back arrow closes the current activity, returning us to the original activity
		Intent data = new Intent();
		data.putExtra("return from b", editText_b.getText().toString() + " by back press" );
		setResult(RESULT_OK, data);
		super.onBackPressed();
	}

}

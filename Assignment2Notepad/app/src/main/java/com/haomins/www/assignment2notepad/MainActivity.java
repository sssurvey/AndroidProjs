package com.haomins.www.assignment2notepad;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.JsonWriter;
//import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

	private TextView update;
	private EditText pad;
	private Product product;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pad = (EditText) findViewById(R.id.pad); 					//this is for the editing area
		update = (TextView) findViewById(R.id.updateInfo); 			//this is for the update of save - time
		update.setText(getString(R.string.Update));
		pad.setMovementMethod(new ScrollingMovementMethod());
		pad.setTextIsSelectable(true);

	}


	//NOT DONE - the way it deal with the update might be wrong
	@Override
	protected void onResume() {
		product = loadFile();
		if (product != null) {
			update.setText(product.getUpdate());
			pad.setText(product.getPad());
		}
		super.onResume();
	}


	@Override
	protected void onPause() {
		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		update.setText(currentDateTimeString);
		product.setUpdate(update.getText().toString());
		product.setPad(pad.getText().toString());
		//saveProduct();
		super.onPause();
	}

	@Override
	protected void onStop() {
		saveProduct();
		super.onStop();
	}

	private Product loadFile() { //currently this is done but the way it deal with the update is wrong - FIXED
		product = new Product();
		try {
			InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
			JsonReader reader = new JsonReader(new InputStreamReader(is, getString(R.string.encoding)));

			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("update")) {
					product.setUpdate(reader.nextString());
				} else if (name.equals("pad")) {
					product.setPad(reader.nextString());
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();

		} catch (FileNotFoundException e) {
			Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	private void saveProduct() {
		try {
			FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

			JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
			writer.setIndent("  ");
			writer.beginObject();
			writer.name("update").value(product.getUpdate());
			writer.name("pad").value(product.getPad());
			writer.endObject();
			writer.close();


			StringWriter sw = new StringWriter();
			writer = new JsonWriter(sw);
			writer.setIndent("  ");
			writer.beginObject();
			writer.name("update").value(product.getUpdate());
			writer.name("pad").value(product.getPad());
			writer.endObject();
			writer.close();

			Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}


}

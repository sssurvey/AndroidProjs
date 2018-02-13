package com.haomins.www.multinote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EditActivity extends AppCompatActivity {
	
	static int id = 1;

	private static final String TAG = "EditActivity";
	private EditText editTitle;
	private EditText editContent;
	private Note note;
	private Intent intent;
	private String title;
	private String content;
	//private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		editTitle = findViewById(R.id.note_title_edit);
		editContent = findViewById(R.id.note_content_edit);
		intent = getIntent();
		note =(Note) intent.getSerializableExtra("note");
		Log.d(TAG, "onCreate: " + note);
		editTitle.setText(note.getTitle());
		editContent.setText(note.getContent());


	}

	@Override
	public void onBackPressed(){
		//need to add reminder if user wants to save
		//super.onBackPressed();
		Log.d(TAG, "onBackPressed: backclicked");
		if(note.getContent().equals(editContent.getText().toString()) && note.getTitle().equals(editTitle.getText().toString())){
			super.onBackPressed();
			return;	//if everything is the same just no popup
		}

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(getString(R.string.dialog_title));
		alert.setMessage(getString(R.string.dialog_question));

		alert.setPositiveButton(getString(R.string.dialog_save_option), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
					title = editTitle.getText().toString();
					content = editContent.getText().toString();
					//implement function to deal with no title
					untitled();
					note.setTitle(title);
					note.setContent(content);
					note.setDate(getDate());
					intent.putExtra("note", note);
					setResult(RESULT_OK, intent);
					EditActivity.super.onBackPressed();
				}
			});

		alert.setNegativeButton(getString(R.string.dialog_discard_option), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				finish();
				//do nothing
				//maybe add a toast later
			}
		});

		AlertDialog alert1 = alert.create();
		alert1.show();

	}

	public String getDate(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("dd-mm-yy HH:mm");
		return date.format(c.getTime());
	}

	public void untitled(){
		if(title.length() == 0){
			Context c = getApplicationContext();
			title = getString(R.string.note_title_hint);
			Toast toast = Toast.makeText(c,getString(R.string.toast_no_title_msg),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}

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
		title = editTitle.getText().toString();
		content = editContent.getText().toString();
		untitled();
		note.setTitle(title);
		note.setDate(getDate());
		note.setContent(content);

		intent.putExtra("note",note);
		setResult(-1,intent);
		finish();

	}
}


package com.haomins.www.multinote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
	private static final String TAG = "MainActivity";

	private RecyclerView recyclerView;
	private NoteAdapter nAdapter;
	private ArrayList<Note> allNote = new ArrayList<>();
	private static final int edit = 1;
	private String filename = "notes.json";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		//recycler
		recyclerView = findViewById(R.id.main_recycler);
		nAdapter = new NoteAdapter(allNote, this);

		recyclerView.setAdapter(nAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		//end of recycler
		new MyAsyncTask().execute();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_main_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.menu_about:
				callAbout();
				return true;
			case R.id.menu_create:
				callCreate();
				nAdapter.notifyDataSetChanged();
				return true;
			default:
				nAdapter.notifyDataSetChanged();
				return super.onOptionsItemSelected(item);
		}
	}


	public void callAbout(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	public void callCreate(){
		Intent intent;
		intent = new Intent(this, EditActivity.class);
		Note note = new Note(allNote.size(), "", "", "");
		intent.putExtra("note", note);
		startActivityForResult(intent,edit);
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data){
		if (request == edit){
			if(result == RESULT_OK){
				Note note = (Note) data.getSerializableExtra("note");
				if (note.getId()<allNote.size()){
					int i = 0;
					for(Iterator iter = allNote.iterator(); iter.hasNext();){
						Note tmp = (Note) iter.next();
						if (tmp.getId() == note.getId()) {
							break;
						}
						i++;
					}
					allNote.remove(i);
					}
					allNote.add(0,note);
					nAdapter.notifyDataSetChanged();
				}
			}
		}
	@Override
	public void onClick(View v) {
		int pos = recyclerView.getChildAdapterPosition(v);
		Note note = allNote.get(pos);
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra("note", note);
		startActivityForResult(intent, edit);
	}

	@Override
	public boolean onLongClick(final View v) {
		final int pos = recyclerView.getChildLayoutPosition(v);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialog_title));
		builder.setMessage(getString(R.string.dialog_question));
		builder.setPositiveButton(getString(R.string.dialog_save_option), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				allNote.remove(pos);
				nAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton(getString(R.string.dialog_discard_option), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		return false;
	}

	protected void onPause() {
		super.onPause();
		try {
			FileOutputStream fos = openFileOutput(filename, 0);
			JsonWriter writer = new JsonWriter(new OutputStreamWriter((OutputStream) fos, "UTF-8"));
			writer.setIndent("    ");
			writer.beginArray();
			for(Iterator iter = allNote.iterator(); iter.hasNext();){
				Note note = (Note) iter.next();
				writer.beginObject();
				writer.name("id").value(note.getId());
				writer.name("title").value(note.getTitle());
				writer.name("date").value(note.getDate());
				writer.name("content").value(note.getContent());
				writer.endObject();
			}
			writer.endArray();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}












	/*public void deleteWithIndex(int i){
		allNote.remove(i);
		nAdapter.notifyDataSetChanged();

	}

	public RecyclerView getRecyclerView() {
		return recyclerView;
	}*/




class MyAsyncTask extends AsyncTask<Void, Void, Void>{

	@Override
	protected Void doInBackground(Void... params) {
		try {
			FileInputStream fis = openFileInput(filename);
			JsonReader reader = new JsonReader(new InputStreamReader((InputStream) fis, "UTF-8"));
			reader.beginArray();
			while(reader.hasNext()){
				reader.beginObject();
				String title="";
				String content="";
				String date="";
				int id=0;
				while(reader.hasNext()){
					String name = reader.nextName();
					if (name.equals("id")){
						id = reader.nextInt();
					}else if(name.equals("title")){
						title = reader.nextString();
					}else if (name.equals("content")){
						content = reader.nextString();
					}else if (name.equals("date")){
						date = reader.nextString();
					}else{
						reader.skipValue();
					}
				}
				Note note = new Note(id,date,title,content);
				allNote.add(note);
				reader.endObject();

			}
			reader.endArray();
			reader.close();
			nAdapter.notifyDataSetChanged();

		} catch (FileNotFoundException e) {
			try {
				FileOutputStream fos = openFileOutput(filename,0);
				fos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
}

package com.haomins.www.multinote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


	private RecyclerView recyclerView;
	private NoteAdapter nAdapter;
	private ArrayList<Note> allNote = new ArrayList<>();



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
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public void callAbout(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	public void callCreate(){
		Intent intent = new Intent(this, EditActivity.class);
		startActivity(intent);
	}

}

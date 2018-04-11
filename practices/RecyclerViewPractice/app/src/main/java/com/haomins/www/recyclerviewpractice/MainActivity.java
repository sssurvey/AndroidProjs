package com.haomins.www.recyclerviewpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	ArrayList<PracticeObj> allPractice = new ArrayList<>();
	RecyclerView rv;
	PracticeObjAdapter poa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recyclerViewSetup();



	}

	public void addTop(View v) {
		allPractice.add(0, new PracticeObj());
		poa.notifyDataSetChanged();
	}

	public void delete(View v){
		allPractice.remove(0);
		poa.notifyDataSetChanged();
	}

	public void recyclerViewSetup(){
		rv = findViewById(R.id.recyclerView);
		poa = new PracticeObjAdapter(allPractice,this);
		rv.setAdapter(poa);
		rv.setLayoutManager(new LinearLayoutManager(this));
		for (int i = 0; i < 20; i++) {
			allPractice.add(new PracticeObj());
		}
	}

}

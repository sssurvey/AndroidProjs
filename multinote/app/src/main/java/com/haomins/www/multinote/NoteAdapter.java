package com.haomins.www.multinote;

/**
 * Created by haominshi on 2/12/18.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class NoteAdapter extends RecyclerView.Adapter<MyViewHolder>{

	private ArrayList<Note> noteList;			//reference to the data source
	private MainActivity mainAct;				//reference to the activity

	public NoteAdapter(ArrayList<Note> nL, MainActivity mA){
		this.noteList = nL;
		mainAct = mA;

	}


	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
		//prepare the layout for list element

		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
					//need add functions
					//this should be one click and enter editActivity and able to edit
			}
		});			//short click do
		//------------------------------------------------------------------------------------------
		itemView.setOnLongClickListener(new View.OnLongClickListener(){
			@Override
			public boolean onLongClick(View view) {
					//need to add functions
					//this should be one long click and then have the ability to delete list item

				return false;
			}
		});		//long click do

		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Note note = noteList.get(position);
		//holder.text_title.setText(note.getTitle());
		holder.text_date.setText(note.getDate());
		//holder.text_preview.setText(note.getContent());

		String title_token = note.getTitle();
		if (title_token.length() <= 5 ){
			holder.text_title.setText(title_token);
		} else {
			holder.text_title.setText(title_token.substring(0,4) + "...");
		}

		String preview_token = note.getContent();
		if (preview_token.length() <= 100){
			holder.text_preview.setText(preview_token);
		} else {
			holder.text_preview.setText(preview_token.substring(0, 100) + "...");
		}
	}

	@Override
	public int getItemCount() {
		return noteList.size();			//return the total size of the list
	}


}

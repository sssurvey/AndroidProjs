package com.haomins.www.recyclerviewpractice;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PracticeRecyclerHolder extends RecyclerView.ViewHolder {
	public TextView name;
	public TextView id;

	public PracticeRecyclerHolder(View v){
		super(v);
		name = (TextView) v.findViewById(R.id.textView_name);
		id = (TextView) v.findViewById(R.id.textView_id);
	}

}

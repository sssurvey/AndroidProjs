package com.haomins.www.multinote;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by haominshi on 2/12/18.
 */


public class MyViewHolder extends RecyclerView.ViewHolder{

	public TextView text_title;				//text title 11 char max
	public TextView text_date;				//text date MM-DD-YYYY
	public TextView text_preview;			//text preview should be 2 lines

	public MyViewHolder(View view){
		super(view);
		text_title = view.findViewById(R.id.text_title);
		text_date = view.findViewById(R.id.text_date);
		text_preview = view.findViewById(R.id.text_preview);
	}

}

package com.haomins.www.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

//import org.w3c.dom.Text;

/**
 * Created by haominshi on 3/31/18.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

	public TextView row_office;
	public TextView row_name;

	public MyViewHolder(View itemView){
		super(itemView);
		row_name = itemView.findViewById(R.id.recyclerview_elements_name);
		row_office = itemView.findViewById(R.id.recyclerview_elements_job_title);
	}
}

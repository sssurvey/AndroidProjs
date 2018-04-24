package com.haomins.www.newsgateway;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SourceAdapter extends ArrayAdapter<NewsSource>{

	ViewHolder viewHolder;

	public SourceAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NewsSource> objects) {
		super(context, resource, objects);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.drawer_element, parent, false );

			viewHolder = new ViewHolder();
			viewHolder.sourcesTV = convertView.findViewById(R.id.sourcesTV);

			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.sourcesTV.setText(getItem(position).getName());
		return convertView;
	}



	static  class ViewHolder{
		TextView sourcesTV;
	}




}


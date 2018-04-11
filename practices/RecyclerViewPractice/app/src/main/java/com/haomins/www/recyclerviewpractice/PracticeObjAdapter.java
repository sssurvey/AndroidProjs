package com.haomins.www.recyclerviewpractice;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class PracticeObjAdapter extends RecyclerView.Adapter<PracticeRecyclerHolder>{
	private List<PracticeObj> practiceObjList;
	private MainActivity mainActivity;


	PracticeObjAdapter(List<PracticeObj> empList, MainActivity ma){
		this.practiceObjList = empList;
		this.mainActivity = ma;
	}


	@NonNull
	@Override
	public PracticeRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_elements,parent,false);
		itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(v.getContext(), "LONG", Toast.LENGTH_LONG).show();
				return true;
			}
		});
		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "SHORT", Toast.LENGTH_SHORT).show();
			}
		});
		return new PracticeRecyclerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull PracticeRecyclerHolder holder, int position) {
		PracticeObj obj = practiceObjList.get(position);
		holder.name.setText(obj.getName());
		holder.id.setText(String.format("%d",obj.getId()));
	}

	@Override
	public int getItemCount() {
		return practiceObjList.size();
	}
}

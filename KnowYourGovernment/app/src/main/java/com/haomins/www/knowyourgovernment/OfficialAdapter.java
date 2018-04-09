package com.haomins.www.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by haominshi on 3/31/18.
 */

public class OfficialAdapter extends RecyclerView.Adapter<MyViewHolder>{
	private List<Official> officialList;
	private MainActivity mainActivity;

	public OfficialAdapter(List<Official> officialList, MainActivity mainActivity){
		this.officialList = officialList;
		this.mainActivity = mainActivity;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_elements, parent, false);
		v.setOnClickListener(mainActivity);
		return new MyViewHolder(v);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Official o = officialList.get(position);
		if(o.getParty() == null){
			holder.row_name.setText(o.getName());
		} else {
			holder.row_name.setText(o.getName()+ "(" + o.getParty() + ")");
		}
		holder.row_office.setText(o.getOfficeName());
	}

	@Override
	public int getItemCount() {
		return officialList.size();
	}
}

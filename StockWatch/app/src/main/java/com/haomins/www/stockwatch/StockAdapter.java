package com.haomins.www.stockwatch;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;




/**
 * Created by haominshi on 3/3/18.
 */

public class StockAdapter extends RecyclerView.Adapter<StockHolder> {
	private List<Stock> stockList;
	private MainActivity mA;

	private static final String TAG = "StockAdapter";
	
	
	public StockAdapter(MainActivity mainActivity, List<Stock> stockList) {
		this.mA = mainActivity;
		this.stockList = stockList;
		Log.d(TAG, "StockAdapter: 1");
	}

	@Override
	public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_row, parent, false);
		view.setOnLongClickListener(mA);
		view.setOnClickListener(mA);
		Log.d(TAG, "onCreateViewHolder: 2");
		return new StockHolder(view);

	}

	@Override
	public void onBindViewHolder(StockHolder holder, int position) {
		//holder ref
		//code = 					(TextView)itemView.findViewById(R.id.stock_code_textView);
		//name = 					(TextView)itemView.findViewById(R.id.stock_description_textView);
		//price = 					(TextView)itemView.findViewById(R.id.stock_price_textView);
		//priceUpDown = 			(TextView)itemView.findViewById(R.id.stock_upDown_textView);
		//priceUpDownPersent = 		(TextView)itemView.findViewById(R.id.stock_upDown_persent_textView);

		Stock stock = stockList.get(position);
		holder.name.setText(stock.getName());
		holder.price.setText(String.valueOf(stock.getPrice()));
		holder.code.setText(stock.getCode());
		Log.d(TAG, "onBindViewHolder: 3");

		if (stock.getPriceUpDown() > 0)	//set symbol and color for stock up down
		{
			holder.priceUpDown.setText("↑ "+String.valueOf(stock.getPriceUpDown()));
			holder.priceUpDownPersent.setText("("+String.valueOf(stock.getPriceUpDownPersent())+"%)");

			holder.name.setTextColor(Color.parseColor("#4CAF50"));
			holder.price.setTextColor(Color.parseColor("#4CAF50"));
			holder.code.setTextColor(Color.parseColor("#4CAF50"));
			holder.priceUpDownPersent.setTextColor(Color.parseColor("#4CAF50"));
			holder.priceUpDown.setTextColor(Color.parseColor("#4CAF50"));


		}else{
			holder.priceUpDown.setText("↓ "+String.valueOf(stock.getPriceUpDown()));
			holder.priceUpDownPersent.setText("("+String.valueOf(stock.getPriceUpDownPersent())+"%)");

			holder.name.setTextColor(Color.parseColor("#F44336"));
			holder.price.setTextColor(Color.parseColor("#F44336"));
			holder.code.setTextColor(Color.parseColor("#F44336"));
			holder.priceUpDownPersent.setTextColor(Color.parseColor("#F44336"));
			holder.priceUpDown.setTextColor(Color.parseColor("#F44336"));
		}
		Log.d(TAG, "onBindViewHolder: 3 after");
	}


	@Override
	public int getItemCount() {
		return stockList.size();
	}

}

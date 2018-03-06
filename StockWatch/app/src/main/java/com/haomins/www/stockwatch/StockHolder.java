package com.haomins.www.stockwatch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;



/**
 * Created by haominshi on 3/3/18.
 */

public class StockHolder extends RecyclerView.ViewHolder{
	TextView code;
	TextView name;
	TextView price;
	TextView priceUpDown;
	TextView priceUpDownPersent;

	public StockHolder(View itemView){
		super(itemView);
		code = 					(TextView)itemView.findViewById(R.id.stock_code_textView);
		name = 					(TextView)itemView.findViewById(R.id.stock_description_textView);
		price = 				(TextView)itemView.findViewById(R.id.stock_price_textView);
		priceUpDown = 			(TextView)itemView.findViewById(R.id.stock_upDown_textView);
		priceUpDownPersent = 	(TextView)itemView.findViewById(R.id.stock_upDown_persent_textView);
	}

}

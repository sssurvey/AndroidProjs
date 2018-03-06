package com.haomins.www.stockwatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener{

	private List<Stock> stockList = new ArrayList<>();
	private static String urlMarketWatch="http://www.marketwatch.com/investing/stock/";

	private RecyclerView recyclerView;
	private SwipeRefreshLayout swiper;
	private StockAdapter stockAdapter;
	private DBHandler dbHandler;
	private ConnectivityManager cm;
	private static MainActivity ma;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
		ma = MainActivity.this;

		recyclerView = (RecyclerView) findViewById(R.id.stock_recyclerView);
		stockAdapter = new StockAdapter(this, stockList);
		recyclerView.setAdapter(stockAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		stockAdapter.notifyDataSetChanged();

		swiper = (SwipeRefreshLayout) findViewById(R.id.swipe_refresher);
		swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				if (networkCheck() == false || stockList.size() == 0) {
					swiper.setRefreshing(false);
					return;
				}

				for (Stock stock : stockList){
					AsyncUpdateLoader aul = new AsyncUpdateLoader(ma);
					aul.execute(stock);
				}
				swiper.setRefreshing(false);
			}
		});

		dbHandler = new DBHandler(this);
		if (networkCheck() == false) {
			return;
		}
		ArrayList<String[]> stocks = dbHandler.loadStock();

		for(String[] tmp: stocks){
			Stock s = new Stock(tmp[0], tmp[1]);
			AsyncDataLoader adl = new AsyncDataLoader(this);
			adl.execute(s);
		}

	}

	public Boolean networkCheck(){
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo !=null && netInfo.isConnectedOrConnecting())return true;
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Network Error");
			builder.setMessage("Cannot connect to internet");
			builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
	}

	//menu creation
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_add, menu);
		return true;
	}

	//menu manage case switching
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()){
			case R.id.menu_about:
				intent = new Intent(this, AboutActivity.class);
				startActivity(intent);
				return true;
			case R.id.menu_add:
				addButton();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void addButton(){
		LayoutInflater inflater = LayoutInflater.from(this);
		final View view = inflater.inflate(R.layout.prompt,null);

		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setMessage("Your stock code here:");
		alert.setMessage("New Stock");
		alert.setView(view);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				EditText inputEditText = (EditText) view.findViewById(R.id.input_textEdit);
				String input = inputEditText.getText().toString();

				if (networkCheck()){
					AsyncSymbolLoader asl = new AsyncSymbolLoader(ma);
					asl.execute(input);
				}
			}

		});

		alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		AlertDialog alertDialog = alert.create();
		alertDialog.show();


	}
	@Override
	public boolean onLongClick(View view){
		final int position = recyclerView.getChildAdapterPosition(view);
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);


		alert.setTitle("Warning");
		alert.setMessage("Choose action");
		alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dbHandler.deleteStock(stockList.get(position).getCode());
				stockList.remove(position);
				stockAdapter.notifyDataSetChanged();
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				return;
			}
		});
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
		return false;
	}

	@Override
	public void onClick(View view) {
			final int position = recyclerView.getChildAdapterPosition(view);
			Stock stock = stockList.get(position);
			String url = urlMarketWatch+stock.getCode();
			Intent i = new Intent((Intent.ACTION_VIEW));
			i.setData(Uri.parse(url));
			startActivity(i);
	}


	public void addData(Stock stock){
		for(Stock each: stockList){
			if(each.getCode().equals(stock.getCode())) return;
		}
		stockList.add(stock);
		Log.d(TAG, "addData: in MainA, stockList added"+stockList);

		Collections.sort(stockList, new Comparator<Stock>() {
			@Override
			public int compare(Stock o1, Stock o2) {
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		dbHandler.addStock(stock);
		stockAdapter.notifyDataSetChanged();
	}

	public void updateData(Stock stock) {
		int i=0;
		for(Stock each: stockList){
			if (each.getCode().equals(stock.getCode())) break;
			i++;
		}
		stockList.remove(i);
		stockList.add(i,stock);
		stockAdapter.notifyDataSetChanged();
	}

}

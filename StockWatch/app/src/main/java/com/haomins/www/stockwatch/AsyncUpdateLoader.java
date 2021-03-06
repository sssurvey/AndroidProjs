package com.haomins.www.stockwatch;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by haominshi on 3/3/18.
 */

/*

Stock Symbol & Company Data
DownloadSource: http://d.yimg.com/aq/autoc
Query Format: http://d.yimg.com/aq/autoc?region=US&lang=en-US&query=user-entered-string
For example, if the user-entered text was “CAI”, your full URL would be: http://d.yimg.com/aq/autoc?region=US&lang=en-US&query=CAI

Stock Financial Data
DownloadSource: https://api.iextrading.com
Query Format: https://api.iextrading.com/1.0/stock/user-specified-symbol/quote
For example, if the selected stock symbol was TSLA, your full URL would be: https://api.iextrading.com/1.0/stock/TSLA/quote
 */



public class AsyncUpdateLoader extends AsyncTask<Stock, Void, String>{
	private static final String prefix = "https://api.iextrading.com/1.0/stock/";
	private static final String postfix = "/quote";
	private MainActivity mA;
	private Stock stock;
	private static final String TAG = "AsyncUpdateLoader";


	public AsyncUpdateLoader(MainActivity mA){
		this.mA = mA;
	}

	@Override
	protected String doInBackground(Stock... stocks) {
		stock = stocks[0];
		StringBuilder sb = new StringBuilder();

		try{
			URL url = new URL(prefix+stock.getCode()+postfix);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader((new InputStreamReader(is)));
			String line;
			while ((line = br.readLine()) != null){
				sb.append(line).append("\n");
			}
			return sb.toString().substring(0);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void parseJson(String s){

		try {
			JSONObject jStock = new JSONObject(s);

			stock.setPriceUpDownPersent(jStock.getDouble("changePercent"));
			stock.setPriceUpDown(jStock.getDouble("change"));
			stock.setPrice(jStock.getDouble("latestPrice"));
			Log.d(TAG, "parseJson: Stock info updated");

		} catch (JSONException e) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mA);
			builder.setTitle("Fail");
			builder.setMessage("Stock Not Found: 400");
			AlertDialog alert = builder.create();
			alert.show();
		}

	}
	@Override
	protected void onPostExecute(String s) {
		parseJson(s);
		if (stock.getPrice() != 0){
			mA.addData(stock);
		}
		return;
	}


}

package com.haomins.www.stockwatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

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

public class AsyncSymbolLoader extends AsyncTask<String, Void, String> {

	private MainActivity mA;
	private final String prefix = "http://d.yimg.com/aq/autoc?region=US&lang=en-US&query=";

	public AsyncSymbolLoader(MainActivity mA){
		this.mA =  mA;
	}

	private static final String TAG = "AsyncSymbolLoader";

	@Override
	protected String doInBackground(String... stocks) {
		StringBuilder sb = new StringBuilder();
		try{
			URL url = new URL(prefix+stocks[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader((new InputStreamReader(is)));
			String line;
			while ((line = br.readLine()) != null){
				sb.append(line).append("\n");
			}

			Log.d(TAG, "doInBackground: xxxxxxxxxxxxx"+sb);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private ArrayList<Stock> parseJson(String s){
		ArrayList<Stock> possibleStocks = new ArrayList<>();

		try {
			JSONObject JObjectMain = new JSONObject(s);
			JSONObject JObjectSecond = JObjectMain.getJSONObject("ResultSet");
			JSONArray arrJSON = JObjectSecond.getJSONArray("Result");

			for (int i =0; i<arrJSON.length(); i++){
				JSONObject jStock = (JSONObject) arrJSON.get(i);
				Stock stock = new Stock(jStock.getString("symbol"), jStock.getString("name"));
				possibleStocks.add(stock);
			}

			Log.d(TAG, "parseJson: returned JSON");

			return possibleStocks;



		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "parseJson: returned NULL");
		return null;

	}

	@Override
	protected void onPostExecute(String s) {
		final ArrayList<Stock> possibleStocks = parseJson(s);
		AlertDialog.Builder alert = new AlertDialog.Builder(mA);
		final Stock[] stock = new Stock[1];

		if (possibleStocks == null) {
			alert.setTitle("Error");
			alert.setMessage("Stock Not Found");
			AlertDialog alertDialog = alert.create();
			alertDialog.show();
			return;
		}
		else if (possibleStocks.size() == 1){
			stock[0] = possibleStocks.get(0);
			AsyncDataLoader adl = new AsyncDataLoader(mA);
			adl.execute(stock[0]);
		}else{
			CharSequence[] stocks = new CharSequence[possibleStocks.size()];
			int i =0;
			for (Stock each : possibleStocks){
				CharSequence tmp = each.getCode()+" - "+each.getName();
				stocks[i] = tmp;
				i++;
			}

			alert.setTitle("Make a selection");
			alert.setItems(stocks, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					stock[0] = possibleStocks.get(which);
					AsyncDataLoader adl = new AsyncDataLoader(mA);
					adl.execute(stock[0]);
				}
			});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});

			AlertDialog alertDialog = alert.create();
			alertDialog.show();
		}

	}
}

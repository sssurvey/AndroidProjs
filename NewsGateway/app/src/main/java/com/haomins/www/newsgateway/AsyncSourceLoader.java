package com.haomins.www.newsgateway;

import android.os.AsyncTask;
import android.widget.Toast;

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

//d88450b8758543b8a69aeaa4a9cac373

public class AsyncSourceLoader extends AsyncTask<String, Void, String>{
	MainActivity ma;
	String prefix_url = "https://newsapi.org/v1/sources?language=en&country=us&category=";
	String postfix_url = "&apiKey=d88450b8758543b8a69aeaa4a9cac373";

	public AsyncSourceLoader(MainActivity ma){
		this.ma = ma;
	}

	@Override
	protected String doInBackground(String... params) {
		StringBuilder sb = new StringBuilder();
		String category = params[0];
		try {
			URL url = new URL(prefix_url+category+postfix_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

			String line;
			while ((line = reader.readLine()) != null) sb.append(line).append("\n");
			return sb.toString();
		} catch (MalformedURLException e) {
			Toast.makeText(ma,"Malformed",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
			Toast.makeText(ma,"ProtocalE",Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ma,"IOE",Toast.LENGTH_LONG).show();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String s) {
			parseJson(s);
		}


	private void parseJson(String s) {
		try {
			JSONObject jObjMain = new JSONObject(s);
			JSONArray jArrSources = jObjMain.getJSONArray("sources");
			ma.clearSource();

			for (int i =0; i<jArrSources.length(); i++){
				JSONObject jObjSource = jArrSources.getJSONObject(i);
				String id = jObjSource.getString("id");
				String name = jObjSource.getString("name");
				String url = jObjSource.getString("url");
				String category = jObjSource.getString("category");

				NewsSource newsSource = new NewsSource(id, name, url, category);
				ma.addSource(newsSource);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			ma.clearSource();
			Toast.makeText(ma,"IOE",Toast.LENGTH_LONG).show();
			String id = "Connection issues";
			String name = "Connection issues";
			String url = "Connection issues";
			String category = "Connection issues";
			NewsSource newsSource = new NewsSource(id, name, url, category);
			ma.addSource(newsSource);
		}

	}
}

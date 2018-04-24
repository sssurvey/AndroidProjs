package com.haomins.www.newsgateway;

import android.os.AsyncTask;

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
	String prefix_url = "https://newsapi.org/v2/sources?apiKey=d88450b8758543b8a69aeaa4a9cac373";

	public AsyncSourceLoader(MainActivity ma){
		this.ma = ma;
	}

	@Override
	protected String doInBackground(String... params) {
		StringBuilder sb = new StringBuilder();
		String category = params[0];
		try {
			URL url = new URL(prefix_url+category);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

			String line;
			while ((line = reader.readLine()) != null) sb.append(line).append("\n");
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
		}

	}
}

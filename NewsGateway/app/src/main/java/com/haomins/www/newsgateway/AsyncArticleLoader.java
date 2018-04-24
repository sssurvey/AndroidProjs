package com.haomins.www.newsgateway;

//API - d88450b8758543b8a69aeaa4a9cac373


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

//https://newsapi.org/v2/sources?apiKey=d88450b8758543b8a69aeaa4a9cac373


public class AsyncArticleLoader extends AsyncTask<String, Void, String> {
	NewsService newsService;
	String url_pre = "https://newsapi.org/v1/articles?source=";
	String url_post = "&apiKey=d88450b8758543b8a69aeaa4a9cac373";
	//works

	public AsyncArticleLoader (NewsService ns){
		this.newsService = ns;
	}

	@Override
	protected String doInBackground(String... params) {
		String source = params[0];
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(url_pre+source+url_post);
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

	@Override
	protected void onPostExecute(String s) {
		parseJson(s);
	}

	void parseJson(String s){
		try{
			JSONObject jObjMain = new JSONObject(s);
			JSONArray jsonArray = jObjMain.getJSONArray("articles");
			for(int i = 0; i<jsonArray.length();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String author = jsonObject.getString("author");
				String title = jsonObject.getString("title");
				String description = jsonObject.getString("description");
				String url = jsonObject.getString("url");
				String urlToImage = jsonObject.getString("urlToImage");
				String publishedAt = jsonObject.getString("publishedAt");

				//Article(String title, String author, String description, String time,
				//String URL, String URL_IMAGE, int total, int index)
				Article article = new Article(title, author, description, publishedAt, url, urlToImage, jsonArray.length(), i);
				newsService.addArticle(article);
				if(isCancelled()){
					return;
				}
			}



		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

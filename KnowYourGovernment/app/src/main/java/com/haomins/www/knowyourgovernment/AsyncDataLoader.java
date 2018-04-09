package com.haomins.www.knowyourgovernment;

import android.os.AsyncTask;
//import android.util.Log;

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

/**
 * Created by haominshi on 3/29/18.
 */

//My API key is : AIzaSyC59dbfg0rLHlUO0ZdZo9YW7C3vwnFx0Gk

public class AsyncDataLoader extends AsyncTask<String, Void, String> {
	private MainActivity mainActivity;
	private String prefix = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyC59dbfg0rLHlUO0ZdZo9YW7C3vwnFx0Gk&address=";
	//api enabled and working 3.29

	public AsyncDataLoader(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	protected String doInBackground(String... params) {
		String location = params[0];



		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(prefix + location);
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


	//need implemntation
	@Override
	protected void onPostExecute(String s) {
		ParseJson(s);
	}


	void ParseJson(String s) {
		try {
			JSONObject jMain = new JSONObject(s);
			JSONObject jNormal = jMain.getJSONObject("normalizedInput");


			String locationText = jNormal.getString("city") + ", " + jNormal.getString("state") + " " + jNormal.getString("zip");
			mainActivity.setLocationText(locationText);
			JSONArray jArrayOffices = jMain.getJSONArray("offices");
			JSONArray jArrayOfficials = jMain.getJSONArray("officials");

			int j_length = jArrayOffices.length();
			mainActivity.clearOfficial();

			for (int i = 0; i < j_length; i++) {
				JSONObject jObj = jArrayOffices.getJSONObject(i);
				String officeName = jObj.getString("name");

				JSONArray indicesStr = jObj.getJSONArray("officialIndices");

				for (int j = 0; j < indicesStr.length(); j++) {
					int pos = Integer.parseInt(indicesStr.getString(j));
					Official official = new Official(officeName);
					JSONObject jOfficial = jArrayOfficials.getJSONObject(pos);

					official.setName(jOfficial.getString("name"));

					JSONArray jAddresses = jOfficial.getJSONArray("address");
					JSONObject jAddress = jAddresses.getJSONObject(0);


					////////////////////////////////////////
					if (jOfficial.has("party")) {
						official.setParty(jOfficial.getString("party"));
					}
					if (jOfficial.has("phones")) {
						official.setPhone(jOfficial.getJSONArray("phones").getString(0));
					}
					if (jOfficial.has("urls")) {
						official.setUrl(jOfficial.getJSONArray("urls").getString(0));
					}
					if (jOfficial.has("emails")) {
						official.setEmail(jOfficial.getJSONArray("emails").getString(0));
					}
					////////////////////////////////////////


					////////////////////address////////////////////
					String address = "";
					if (jAddress.has("line1")) {
						address += jAddress.getString("line1") + '\n';
					}
					if (jAddress.has("line2")) {
						address += jAddress.getString("line2") + '\n';
					}
					if (jAddress.has("line3")) {
						address += jAddress.getString("line3") + '\n';
					}
					if (jAddress.has("city")) {
						address += jAddress.getString("city") + ", ";
					}
					if (jAddress.has("state")) {
						address += jAddress.getString("state") + ' ';
					}
					if (jAddress.has("zip")) {
						address += jAddress.getString("zip");
					}
					official.setAddress(address);
					////////////////////////////////////////

					////////////////////buttons social network////////////////////
					if (jOfficial.has("channels")) {
						Channel channel = new Channel();

						JSONArray jChannels = jOfficial.getJSONArray("channels");
						for (int k = 0; k < jChannels.length(); k++) {
							JSONObject jChannel = jChannels.getJSONObject(k);
							if (jChannel.getString("type").equals("Facebook"))
								channel.setFacebook(jChannel.getString("id"));

							if (jChannel.getString("type").equals("GooglePlus"))
								channel.setGoogleplus(jChannel.getString("id"));


							if (jChannel.getString("type").equals("YouTube"))
								channel.setYoutube(jChannel.getString("id"));

							if (jChannel.getString("type").equals("Twitter"))
								channel.setTwitter(jChannel.getString("id"));
						}
						official.setChannel(channel);
					}
					////////////////////////////////////////////////////////////


					////////////////////////////////////////
					if (jOfficial.has("photoUrl"))
						official.setPhotoUrl(jOfficial.getString("photoUrl"));
					mainActivity.addOfficial(official);
					////////////////////////////////////////
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}
}

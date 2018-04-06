package com.haomins.www.knowyourgovernment;

import java.io.Serializable;

/**
 * Created by haominshi on 3/31/18.
 */

public class Channel implements Serializable{
	String facebook, youtube, googleplus,twitter;

	public String getFacebook() {
		return facebook;
	}

	public String getGoogleplus() {
		return googleplus;
	}

	public String getTwitter() {
		return twitter;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public void setGoogleplus(String googleplus) {
		this.googleplus = googleplus;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
}

package com.haomins.www.knowyourgovernment;

import java.io.Serializable;
//import java.nio.channels.Channel;

/**
 * Created by haominshi on 3/31/18.
 */

public class Official implements Serializable {
	String officeName, name, address, party, phone, url, email, photoUrl = "No Data Provided";
	Channel channel; //Channel refer to the social media

	///////////
	public Official(String officeName){this.officeName = officeName;}
	public Official(String officeName, String name){
		this.name = name;
		this.officeName = officeName;
	}
	///////////


	public String getName() {
		return name;
	}

	public Channel getChannel() {
		return channel;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getOfficeName() {
		return officeName;
	}

	public String getParty() {
		return party;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public String getUrl() {
		return url;
	}

	///////////


	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	///////////
}

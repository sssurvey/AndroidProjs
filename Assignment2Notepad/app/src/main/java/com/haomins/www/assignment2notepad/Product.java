package com.haomins.www.assignment2notepad;

/**
 * Created by haominshi on 1/30/18.
 */

public class Product {

	private String update;
	private String pad;

	public String getPad() {
		return pad;
	}

	public void setPad(String pad) {
		this.pad = pad;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String toString() {
		return update + ": " + pad;
	}
}

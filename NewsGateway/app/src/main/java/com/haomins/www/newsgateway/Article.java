package com.haomins.www.newsgateway;

import java.io.Serializable;

public class Article implements Serializable{
	String title, author, description, time;
	String URL, URL_IMAGE;

	int total, index;

	public Article(String title, String author, String description, String time, String URL, String URL_IMAGE, int total, int index) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.time = time;
		this.URL = URL;
		this.URL_IMAGE = URL_IMAGE;
		this.total = total;
		this.index = index+1;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public String getTime() {
		return time;
	}

	public String getURL() {
		return URL;
	}

	public String getURL_IMAGE() {
		return URL_IMAGE;
	}

	public int getTotal() {
		return total;
	}

	public int getIndex() {
		return index;
	}
}

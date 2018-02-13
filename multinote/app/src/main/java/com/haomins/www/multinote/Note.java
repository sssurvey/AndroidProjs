package com.haomins.www.multinote;

/**
 * Created by haominshi on 2/12/18.
 */

import java.io.Serializable;

public class Note implements Serializable{

	private int id;
	private String title;
	private String content;
	private String date;

	//private static int id = 1;
	public Note(int id, String date, String title, String content){
		this.id = id;
		this.date = date;
		this.title = title;
		this.content = content;


	}



	public void setTitle(String title){
		this.title = title;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setDate(String date) {this.date = date;}
	public void setId(int id) {this.id = id;}

	public String getTitle(){return title;}
	public String getContent(){
		return content;
	}
	public String getDate() {return date; }

	public int getId() {
		return id;
	}
}

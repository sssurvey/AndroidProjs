package com.haomins.www.multinote;

/**
 * Created by haominshi on 2/12/18.
 */

public class Note {

	private String title;
	private String content;
	private String date;
	//private static int id = 1;


	public void setTitle(String title){
		this.title = title;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setDate(String date) {this.date = date;}

	public String getTitle(){return title;}
	public String getContent(){
		return content;
	}
	public String getDate() {return date; }
}

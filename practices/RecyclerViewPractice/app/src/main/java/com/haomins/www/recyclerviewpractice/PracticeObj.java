package com.haomins.www.recyclerviewpractice;

public class PracticeObj {
	private static int count = 1;
	String name;
	int id;

	PracticeObj(){

		this.name = "Name is: "+ count;
		this.id = count++;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static int getCount() {
		return count;
	}
}

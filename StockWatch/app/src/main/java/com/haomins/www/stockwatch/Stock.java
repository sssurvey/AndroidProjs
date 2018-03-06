package com.haomins.www.stockwatch;

/**
 * Created by haominshi on 3/3/18.
 */

public class Stock {
	private String code;
	private String name;
	private double price;
	private double priceUpDown;
	private double priceUpDownPersent;

	public Stock(String c, String n){
		this.code = c;
		this.name = n;
	}

	public Stock(String c, String n, double p, double pud, double pudp){
		this.code = c;
		this.name = n;
		this.price = p;
		this.priceUpDown = pud;
		this.priceUpDownPersent = pudp;
	}


	//getters
	public String getCode(){
		return code;
	}

	public String getName() {
		return name;
	}

	public double getPrice(){
		return price;
	}

	public double getPriceUpDown() {
		return priceUpDown;
	}

	public double getPriceUpDownPersent() {
		return priceUpDownPersent;
	}

	//setters
	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setPriceUpDown(double priceUpDown) {
		this.priceUpDown = priceUpDown;
	}

	public void setPriceUpDownPersent(double priceUpDownPersent) {
		this.priceUpDownPersent = priceUpDownPersent;
	}

}

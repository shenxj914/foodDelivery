package com.css.kitchen.orders;

import org.json.simple.JSONObject;

public class Order {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public long getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(long shelfLife) {
		this.shelfLife = shelfLife;
	}
	public double getDecayRate() {
		return decayRate;
	}
	public void setDecayRate(double decayRate) {
		this.decayRate = decayRate;
	}
	
	//use json library
	public Order(JSONObject obj) {
		this((String)obj.get("name"), (String)obj.get("temp"), 
				(Long)obj.get("shelfLife"), (Double)obj.get("decayRate"),(String)obj.get("id"));
	}
	
	
	
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", temp=" + temp + ", shelfLife=" + shelfLife + ", decayRate="
				+ decayRate + "]";
	}
	private String name;
	public Order(String name, String temp, long shelfLife, double decayRate, String id) {
		super();
		this.name = name;
		this.temp = temp;
		this.shelfLife = shelfLife;
		this.decayRate = decayRate;
		this.id = id;
	}
	private String temp;
	private long shelfLife;
	private double decayRate;
	private String id;
}

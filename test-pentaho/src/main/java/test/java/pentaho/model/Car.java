package test.java.pentaho.model;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class Car implements Serializable {
	private String id;
	private String brand;
	private int year;
	private String color;
	private int price;

	public Car(String id, String brand, int year, String color, int price) {
		this.brand = brand;
		this.year = year;
		this.color = color;
		this.price = price;
	}

	public Object[] toObjects() {
		List<Object> list = Lists.newArrayList();
		list.add(brand);
		list.add(year);
		list.add(color);
		list.add(price);
		return list.toArray();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

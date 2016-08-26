package test.java.pentaho.service;

import test.java.pentaho.model.Car;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Stateless
public class CarServiceBean implements CarService {

	private final static String[] colors;
	private final static String[] brands;

	static {
		colors = new String[10];
		colors[0] = "Black";
		colors[1] = "White";
		colors[2] = "Green";
		colors[3] = "Red";
		colors[4] = "Blue";
		colors[5] = "Orange";
		colors[6] = "Silver";
		colors[7] = "Yellow";
		colors[8] = "Brown";
		colors[9] = "Maroon";

		brands = new String[10];
		brands[0] = "BMW";
		brands[1] = "Mercedes";
		brands[2] = "Volvo";
		brands[3] = "Audi";
		brands[4] = "Lotus";
		brands[5] = "Mustang";
		brands[6] = "Chevrolet";
		brands[7] = "Honda";
		brands[8] = "Jaguar";
		brands[9] = "Ford";
	}

	@Override
	public List<Car> getCars(int n) {
		List<Car> list = new ArrayList<Car>();
		for(int i = 0 ; i < n ; i++) {
			list.add(new Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice()));
		}
		return list;
	}

	private String getRandomId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	private int getRandomYear() {
		return (int) (Math.random() * 50 + 1960);
	}

	private String getRandomColor() {
		return colors[(int) (Math.random() * 10)];
	}

	private String getRandomBrand() {
		return brands[(int) (Math.random() * 10)];
	}

	public int getRandomPrice() {
		return (int) (Math.random() * 100000);
	}

	public boolean getRandomSoldState() {
		return (Math.random() > 0.5) ? true: false;
	}

	public List<String> getColors() {
		return Arrays.asList(colors);
	}

	public List<String> getBrands() {
		return Arrays.asList(brands);
	}
}

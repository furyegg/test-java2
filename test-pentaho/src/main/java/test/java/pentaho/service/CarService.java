package test.java.pentaho.service;

import test.java.pentaho.model.Car;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CarService {
	List<Car> getCars(int n);
}
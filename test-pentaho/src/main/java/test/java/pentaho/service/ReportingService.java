package test.java.pentaho.service;

import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import test.java.pentaho.model.Car;

import javax.ejb.Local;
import java.io.OutputStream;
import java.util.List;

@Local
public interface ReportingService {
	void generatePDFReport(List<Car> cars, OutputStream out) throws ReportProcessingException;
}
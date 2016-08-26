package test.java.pentaho.controller;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import test.java.pentaho.model.Car;
import test.java.pentaho.service.CarService;
import test.java.pentaho.service.ReportingService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

@Named
@ViewAccessScoped
public class CarBean implements Serializable {

	@Inject
	private CarService carService;

	@Inject
	private ReportingService reportingService;

	private List<Car> cars;

	private StreamedContent pdfContent;

	private boolean generated = false;

	@PostConstruct
	public void init() throws Exception {
		cars = carService.getCars(50);
//		generateReport();
	}

	public void generateReport() throws Exception {
		ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
		reportingService.generatePDFReport(cars, pdfOut);

		InputStream inputStream = new ByteArrayInputStream(pdfOut.toByteArray());
		pdfContent = new DefaultStreamedContent(inputStream, "application/pdf", "example.pdf");

		IOUtils.closeQuietly(pdfOut);
		generated = true;
	}

	public List<Car> getCars() {
		return cars;
	}

	public StreamedContent getPdfContent() {
		return pdfContent;
	}

	public boolean isGenerated() {
		return generated;
	}
}
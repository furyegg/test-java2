package test.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {
	public void warning(SAXParseException e) throws SAXException {
		System.out.println("Validation Warning: " + e.getMessage());
	}

	public void error(SAXParseException e) throws SAXException {
		System.out.println("Validation Error: " + e.getMessage());
	}

	public void fatalError(SAXParseException e) throws SAXException {
		System.out.println("Validation FatalError: " + e.getMessage());
	}
}
package jaxp.validate;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import constant.Const;

public class GenerateTest {

	private static final String PATH = Const.PATH_SRC + "jaxp/validate/";
	private static final String PATH_DOC = PATH + "document.xml";
	private static final String PATH_XSD = PATH + "simple.xsd";
	
	public static String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
			XML_SCHEMA = "http://www.w3.org/2001/XMLSchema",
			SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public final static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		File input = new File(PATH_DOC);
		File schema = new File(PATH_XSD);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		try {
			factory.setAttribute(SCHEMA_LANGUAGE, XML_SCHEMA);
			factory.setAttribute(SCHEMA_SOURCE, schema);
		} catch (IllegalArgumentException x) {
			System.err.println("Your DOM parser is not JAXP 1.2 compliant.");
		}
		DocumentBuilder parser = factory.newDocumentBuilder();
		parser.setErrorHandler(new ErrorPrinter());
		parser.parse(input);
	}
	
}

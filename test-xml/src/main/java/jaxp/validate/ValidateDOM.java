package jaxp.validate;

// requires JAXP 1.2

import java.io.*;
import org.xml.sax.*;

import constant.Const;

import javax.xml.parsers.*;

public class ValidateDOM {
	
	private static final String PATH = Const.PATH_SRC + "jaxp/validate/";
	private static final String PATH_DOC = PATH + "corep_conpru_c_08.02.xbrl";
	private static final String PATH_XSD =  "D:/xbrl/eba/eu/fr/xbrl/crr/fws/corep/its-2013-02/2013-12-01/mod/corep_con.xsd";
	private static final String PATH_XSD2 = "D:/xbrl/eba/eu/fr/xbrl/crr/fws/corep/its-2013-02/2013-12-01/tab/c_00.01/c_00.01.xsd";
	
	public static String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
			XML_SCHEMA = "http://www.w3.org/2001/XMLSchema",
			SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public final static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		File input = new File(PATH_DOC);
		File schema = new File(PATH_XSD);
		File table1 = new File(PATH_XSD2);
		
		System.out.println("schema existed: " + schema.exists());
		System.out.println("table existed: " + table1.exists());
		
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
		
		System.out.println("Valid!");
	}
}
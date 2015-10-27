package comparation.validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.codehaus.stax2.validation.XMLValidationSchema;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import comparation.Timer;
import constant.Const;

public class JAXPValidator {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		Timer t = new Timer();
		t.begin();

		File xmlFile = new File(Const.XML_VAL);
		FileInputStream fis = new FileInputStream(xmlFile);

		// System.setProperty("javax.xml.parsers.SAXParserFactory", "com.ctc.wstx.sax.WstxSAXParserFactory");
		// internalXsd();
		externalXsd(fis);
		// validateXbrl();

		fis.close();
		t.end();
	}
	
	private static void validateXbrl() throws SAXException, IOException, ParserConfigurationException {
		File xmlFile = new File(Const.XML_XBRL);
		FileInputStream fis = new FileInputStream(xmlFile);
		
		// System.setProperty("javax.xml.parsers.SAXParserFactory", "com.ctc.wstx.sax.WstxSAXParserFactory");
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(Const.XSD_XBRL) }));
		
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(new SimpleErrorHandler());
		reader.parse(new InputSource(fis));
	}
	
	private static void externalXsd(FileInputStream fis) throws SAXException, IOException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
		factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(Const.XSD_BOOK) }));
		
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(new SimpleErrorHandler());
		reader.parse(new InputSource(fis));
	}
	
	private static void internalXsd() throws SAXException, IOException, ParserConfigurationException {
//		File xmlFile = new File(Const.PATH_SRC + "corep_con.xbrl");
		File xmlFile = new File(Const.XML_VAL);
		FileInputStream fis = new FileInputStream(xmlFile);
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		
		SAXParser parser = factory.newSAXParser();
		parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(new SimpleErrorHandler());
		reader.parse(new InputSource(fis));
	}

}
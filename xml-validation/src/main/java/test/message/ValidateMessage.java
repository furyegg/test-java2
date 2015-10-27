package test.message;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import test.xml.JAXPValidator.DumbDOMResourceResolver;
import test.xml.SimpleErrorHandler;

public class ValidateMessage {

	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		ValidateMessage app = new ValidateMessage();
		app.validate();
	}

	public void validate() throws SAXException, ParserConfigurationException, IOException {
		// InputStream xmlIn = this.getClass().getResourceAsStream("/3_2999_20140610-new.xml");
		File xmlFile = new File("src/main/resources/3_2999_20140610-new.xml");
		String xmlFileString = FileUtils.readFileToString(xmlFile);
		ByteArrayInputStream xmlIn = new ByteArrayInputStream(xmlFileString.getBytes("UTF-8"));

		InputStream lrmMsgIn = this.getClass().getResourceAsStream("/xsd/ocelot-exp-imp.xsd");
		InputStream oceExpimpIn = this.getClass().getResourceAsStream("/xsd/lrm-messaging.xsd");

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		schemaFactory.setResourceResolver(new DumbDOMResourceResolver());

		Source[] sources = new Source[2];
		sources[0] = new StreamSource(oceExpimpIn);
		sources[1] = new StreamSource(lrmMsgIn);
		Schema schema = schemaFactory.newSchema(sources);
		factory.setSchema(schema);

		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(new SimpleErrorHandler());
		reader.parse(new InputSource(xmlIn));

		xmlIn.close();
		oceExpimpIn.close();
		lrmMsgIn.close();

		System.out.println("Validation Done.");
	}

}

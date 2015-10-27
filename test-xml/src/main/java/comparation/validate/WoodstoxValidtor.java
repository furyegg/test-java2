package comparation.validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.xml.sax.SAXException;

import comparation.Timer;

import constant.Const;

public class WoodstoxValidtor {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XMLStreamException {
		Timer t = new Timer();
		t.begin();

		File xmlFile = new File(Const.XML_XBRL);
//		File xmlFile = new File("C:/xbrl/CRGB_2999_20140610.xbrl");
		FileInputStream fis = new FileInputStream(xmlFile);

		// System.setProperty("javax.xml.parsers.SAXParserFactory", "com.ctc.wstx.sax.WstxSAXParserFactory");
		// internalXsd();
		externalXsd(fis);

		fis.close();
		t.end();
	}

	private static void externalXsd(FileInputStream fis) throws SAXException, IOException, ParserConfigurationException, XMLStreamException {
//		URL xsdUrl1 = new URL("http://www.eba.europa.eu/eu/fr/xbrl/crr/fws/corep/its-2013-02/2013-12-01/mod/corep_con.xsd");
//		URL xsdUrl2 = new URL("http://www.eba.europa.eu/eu/fr/xbrl/crr/dict/met/met.xsd");
		File xsdUrl1 = new File("D:/jaxp/eba_corep_2.0.2.0/www.eba.europa.eu/eu/fr/xbrl/crr/fws/corep/its-2013-02/2014-03-31/mod/corep_con.xsd");
		File xsdUrl2 = new File("D:/jaxp/eba_dict_2.1.0.1/www.eba.europa.eu/eu/fr/xbrl/crr/dict/met/met.xsd");
		
		XMLValidationSchemaFactory xsdFactory = XMLValidationSchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
		// XMLValidationSchema schema = xsdFactory.createSchema(new FileInputStream(Const.XSD_XBRL));
		XMLValidationSchema schema1 = xsdFactory.createSchema(xsdUrl1);
		XMLValidationSchema schema2 = xsdFactory.createSchema(xsdUrl2);

		XMLInputFactory2 inFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
		XMLStreamReader2 reader = (XMLStreamReader2) inFactory.createXMLStreamReader(fis);

		reader.validateAgainst(schema1);
		reader.validateAgainst(schema2);

		while (reader.hasNext()) {
			reader.next();
		}
	}

}
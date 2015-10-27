package test.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;

public class JAXPValidator {

	public static class DumbDOMResourceResolver implements LSResourceResolver {
		@Override
		public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
				String baseURI) {
			System.out.println(String.format("Type=%s, NsURI=%s, PublicId=%s, SystemId=%s, BaseURI=%s",  type, namespaceURI, publicId, systemId, baseURI));
			
//			URL url = null;
//			try {
//				url = new URL(baseURI);
//				
//				InputStream xsdIn = url.openStream();
//				ByteArrayOutputStream xsdOut = new ByteArrayOutputStream(xsdIn.available());
//				IOUtils.copy(xsdIn, xsdOut);
//				InputStream modifiedXsdIn = new ByteArrayInputStream(xsdOut.toByteArray());
//				return new DOMInputImpl(publicId, systemId, baseURI, xsdIn, "UTF-8");
//				
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			return null;
		}
	}

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		Timer t = new Timer();
		t.begin();
		validateXbrl();
		t.end();
	}

	private static void validateXbrl() throws SAXException, IOException, ParserConfigurationException {
		InputStream fis = new FileInputStream(Const.XML_XBRL2);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		schemaFactory.setResourceResolver(new DumbDOMResourceResolver());

		Source[] sources = new Source[2];
		sources[0] = new StreamSource(Const.XSD_XBRL_LOCAL);
		sources[1] = new StreamSource(Const.XSD_MET);
		Schema schema = schemaFactory.newSchema(sources);
		factory.setSchema(schema);

		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(new SimpleErrorHandler());
		reader.parse(new InputSource(fis));
		fis.close();
	}

}
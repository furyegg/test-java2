import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import constant.Const;

public class JAXPTest {

	public static void main(String[] args) {
		try {
			// Get SAX Parser Factory
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// Turn on validation, and turn off namespaces
			factory.setValidating(true);
			factory.setNamespaceAware(false);
			SAXParser parser = factory.newSAXParser();
			parser.parse(Const.TEST_XML, new MyHandler());

		} catch (ParserConfigurationException e) {
			System.out.println("The underlying parser does not support " + " the requested features.");
		} catch (FactoryConfigurationError e) {
			System.out.println("Error occurred obtaining SAX Parser Factory.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class MyHandler extends DefaultHandler {

}
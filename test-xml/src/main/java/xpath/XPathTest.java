package xpath;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XPathTest {

	public static void main(String[] args) throws Exception {
		jaxpDom();
	}

	private static void jaxpDom() throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newInstance().newDocumentBuilder();
		InputStream fis = new FileInputStream("src/main/java/xpath/met.xsd");
		Document doc = builder.parse(fis);
		
		InputSource inputSource = new InputSource(fis);

		NamespaceContext nsCxt = new NamespaceContext() {
			@Override
			public Iterator getPrefixes(String namespaceURI) {
				return null;
			}

			@Override
			public String getPrefix(String namespaceURI) {
				return "http://www.eurofiling.info/xbrl/ext/model";
			}

			@Override
			public String getNamespaceURI(String prefix) {
				return "model";
			}
		};

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(nsCxt);
		// xpath.evaluate("", source)
	}

}

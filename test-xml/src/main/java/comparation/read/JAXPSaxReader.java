package comparation.read;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import comparation.Timer;
import constant.Const;


public class JAXPSaxReader {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Timer t = new Timer();
		t.begin();
		
		File xmlFile = new File(Const.XML_VAL);
		
		DefaultHandler handler = new DefaultHandler() {
			@Override
			public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) {
				if (lname.equals("book") && attrs != null) {
					StringBuilder res = new StringBuilder(qname).append(namespaceURI);
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				StringBuilder buffer = new StringBuilder();
				int end = start + length;
				for (int i = start; i < end; ++i) {
					switch (ch[i]) {
					case '\\':
//						buffer.append("\\\\");
						break;
					case '\r':
//						buffer.append("\\r");
						break;
					case '\n':
//						buffer.append("\\n");
						break;
					case '\t':
//						buffer.append("\\t");
						break;
					case '\"':
//						buffer.append("\\\"");
						break;
					default:
						buffer.append(ch[i]);
					}
				}
				// System.out.println("characters(" + length + "): " + buffer.toString());
			}
		};
		
		// System.setProperty("javax.xml.parsers.SAXParserFactory", "com.ctc.wstx.sax.WstxSAXParserFactory");
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		SAXParser parser = factory.newSAXParser();
		
		parser.parse(xmlFile, handler);
		
		t.end();
	}
	
}
package jaxp;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XBRLGenerator {
	
	private static final String XBRLI_URI = "http://www.xbrl.org/2003/instance";
	private static final String FIND_URI = "http://www.eurofiling.info/xbrl/ext/filing-indicators";

	public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		
		// factory.
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element root = doc.createElementNS(XBRLI_URI, "xbrli:xbrl");
		doc.appendChild(root);
//		Attr findNs = doc.createAttributeNS(FIND_URI, "find");
//		root.setAttributeNodeNS(findNs);
		
		Element find = doc.createElementNS(XBRLI_URI, "find:fIndicators");
		root.appendChild(find);
		
		Element unit = doc.createElementNS(XBRLI_URI, "xbrli:unit");
		root.appendChild(unit);

		outputDoc(doc);
	}

	private static void outputDoc(Document doc) throws IOException, TransformerException {
		// OutputStream out = new FileOutputStream(Const.PATH_SRC + "jaxp/xbrl.txt");
		OutputStream out = System.out;

		DOMSource domSource = new DOMSource(doc);
		StreamResult streamResult = new StreamResult(out);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.transform(domSource, streamResult);
	}

}

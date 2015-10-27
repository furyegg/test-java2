

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class TestXslt {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File("src/main/resources/x.xml");
		File resultFile = new File("src/main/resources/xslt_res.xml");
		File xsltFile = new File("src/main/resources/test2.xsl");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xsltDoc = builder.parse(xsltFile);

		Source xmlSource = new StreamSource(xmlFile);
		// Source xsltSource = new DOMSource(xsltDoc);
		Source xsltSource = new StreamSource(xsltFile);
		Result xmlResult = new StreamResult(resultFile);
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);
		trans.setParameter("fileName", "y.xml");

		trans.transform(xmlSource, xmlResult);
	}

}

package comparation.write;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import constant.Const;

public class JAXPDomWriter {

	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		// factory.setValidating(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.newDocument();
		
		Element root = doc.createElement("books");
		doc.appendChild(root);
		
		for (int i = 0; i < Const.NODE_COUNTS_M; ++i) {
			Element book = doc.createElement("books");
			book.setAttribute("id", String.valueOf(i + 1));
			
			Element name = doc.createElement("name");
			name.setTextContent("Name" + (i + 1));
			book.appendChild(name);
			
			Element author = doc.createElement("author");
			author.setTextContent("author" + (i + 1));
			book.appendChild(author);
			
			root.appendChild(book);
		}
		
		outputDoc(doc);
	}
	
	private static void outputDoc(Document doc) throws IOException, TransformerException {
		FileOutputStream out = new FileOutputStream(Const.PATH_XML + "JAXPDom.xml");
		StreamResult result = new StreamResult(out);
		
		DOMSource domSource = new DOMSource(doc);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(domSource, result);
		System.out.println("Done");
	}
	
}

package comparation.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ParsingException;
import nux.xom.io.StaxParser;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import comparation.Timer;
import constant.Const;

public class XOMReader {

	public static void main(String[] args) throws IOException, DocumentException, SAXException, ParsingException {
		Timer t = new Timer();
		t.begin();

		File xmlFile = new File(Const.XML_BIG);
		FileInputStream fis = new FileInputStream(xmlFile);

		// saxReader(fis);
		staxReader(fis);
		
		fis.close();
		t.end();
	}

	private static void saxReader(InputStream in) throws DocumentException, SAXException, ParsingException, IOException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		Builder builder = new Builder(reader);
		
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		int childCount = root.getChildCount();
		System.out.println(childCount);
		
		for (int i = 0; i < childCount; ++i) {
			Node child = root.getChild(i);
			StringBuilder res = new StringBuilder();
			
			Node name = child.getChild(0);
			Node author = child.getChild(1);
			res.append(name.getValue()).append(author.getValue());
			
			// System.out.println(res.toString());
		}
	}

	private static void staxReader(InputStream in) {
		// StaxParser parser = stax
	}

}
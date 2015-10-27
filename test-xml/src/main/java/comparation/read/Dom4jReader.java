package comparation.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import comparation.Timer;

import constant.Const;

public class Dom4jReader {

	public static void main(String[] args) throws IOException, DocumentException {
		Timer t = new Timer();
		t.begin();

		File xmlFile = new File(Const.XML_BIG);
		FileInputStream fis = new FileInputStream(xmlFile);

		saxReader(fis);
		// staxReader(fis);
		
		fis.close();
		t.end();
	}

	private static void saxReader(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		
		Map<String, String> nameSpaceMap = new HashMap<String, String>();  
	    nameSpaceMap.put("bk", Const.NSURI_BOOK);  
	    reader.getDocumentFactory().setXPathNamespaceURIs(nameSpaceMap);
	    
	    Document doc = reader.read(in);
		
		// List bookList = doc.selectNodes("//bk:book");
	    Element root = doc.getRootElement();
	    List bookList = root.elements("book");
		Iterator itr = bookList.iterator();
		while (itr.hasNext()) {
			StringBuilder res = new StringBuilder();
			
			Element book = (Element) itr.next();
			Element name = book.element("name");
			Element author = book.element("author");
			res.append(name.getText()).append(author.getText());
			
			// System.out.println(res.toString());
		}
	}

	private static void staxReader(InputStream in) {

	}

}
package comparation.write;

import java.io.FileOutputStream;
import java.io.IOException;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;
import nux.xom.io.StreamingSerializer;
import nux.xom.io.StreamingSerializerFactory;

import comparation.Timer;

import constant.Const;

public class XOMWriter {

	public static void main(String[] args) throws IOException {
		Timer t = new Timer();
		t.begin();

		StreamingSerializerFactory factory = new StreamingSerializerFactory();

		FileOutputStream out = new FileOutputStream(Const.PATH_XML + "NUX.xml");
		StreamingSerializer ser = factory.createXMLSerializer(out, "UTF-8");
		
		// StreamingSerializer ser = factory.createBinaryXMLSerializer(System.out, 0);

		ser.writeXMLDeclaration();
		ser.writeStartTag(new Element("books"));
		for (int i = 0; i < Const.NODE_COUNTS_S; i++) {
			Element article = new Element("book");
			article.addAttribute(new Attribute("id", String.valueOf(i + 1)));

			Element name = new Element("name");
			name.appendChild("Name" + String.valueOf(i + 1));
			article.appendChild(name);

			Element author = new Element("author");
			author.appendChild("author" + String.valueOf(i + 1));
			article.appendChild(author);

			ser.write(article); // writes entire subtree
		}
		ser.writeEndTag(); // close articles
		ser.writeEndDocument();
		
		out.close();

		t.end();
	}

}
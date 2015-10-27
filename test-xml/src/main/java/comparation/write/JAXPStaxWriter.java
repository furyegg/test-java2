package comparation.write;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import comparation.Timer;

import constant.Const;

public class JAXPStaxWriter {

	public static void main(String[] args) throws IOException, XMLStreamException {
		Timer t = new Timer();
		t.begin();
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		// factory.setProperty(name, value);
		
		FileOutputStream out = new FileOutputStream(Const.PATH_XML + "JAXPStax.xml");
		XMLStreamWriter writer = factory.createXMLStreamWriter(out);
		writer.writeStartDocument("UTF-8", "1.0");

		writer.writeComment("Comments for books");

		writer.writeStartElement("books");
		for (int i = 0; i < 10; ++i) {
			writer.writeComment("Comments for book============");
			writer.writeStartElement("book");
			writer.writeAttribute("id", String.valueOf(i + 1));
			
			writer.writeStartElement("name");
			writer.writeCharacters("Name" + (i + 1));
			writer.writeEndElement();
			
			writer.writeStartElement("author");
			writer.writeCharacters("author" + (i + 1));
			writer.writeEndElement();
			
			writer.writeEndElement();
		}
		writer.writeEndElement();
		writer.writeEndDocument();
		writer.close();
		out.close();
		
		t.end();
	}
	
}
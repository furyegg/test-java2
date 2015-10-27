package comparation.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.evt.XMLEventFactory2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;

import comparation.Timer;
import constant.Const;

public class WoodstoxWriter {

	public static void main(String[] args) throws IOException, XMLStreamException {
		Timer t = new Timer();
		t.begin();

		// System.setProperty("javax.xml.parsers.SAXParserFactory", "com.ctc.wstx.sax.WstxSAXParserFactory");

		String fileName = Const.PATH_XML + "books.xml";
		FileOutputStream out = new FileOutputStream(Const.PATH_XML + "books.xml");

		streamValidatedWriter(fileName);

		out.close();
		t.end();
	}

	private static void streamWriter(String fileName) throws IOException, XMLStreamException {
		XbrliWriterBean writer = new XbrliWriterBean();
		writer.init(fileName);

		// writer.writeValue("\n");
		writer.writeStartElement("bk", "books", Const.NSURI_BOOK);
		writer.writeNamespace("bk", Const.NSURI_BOOK);

		for (int i = 0; i < Const.NODE_COUNTS_S; ++i) {
			writer.writeStartElement("bk", "book", Const.NSURI_BOOK);
			writer.writeAttribute("id", String.valueOf((i + 1)));

			writer.writeStartElement("bk", "name", Const.NSURI_BOOK);
			String name = new StringBuilder("Name").append(i + i).toString();
			writer.writeValue(name);
			writer.writeEndElement();

			writer.writeStartElement("bk", "author", Const.NSURI_BOOK);
			writer.writeValue("author" + (i + 1));
			writer.writeEndElement();

			writer.writeEndElement();
		}
		writer.writeEndElement();
		writer.close();
	}
	
	private static void streamValidatedWriter(String fileName) throws IOException, XMLStreamException {
		XbrliWriterValidateBean writer = new XbrliWriterValidateBean();
		writer.init(fileName);

		// writer.writeValue("\n");
		writer.writeStartElement("bk", "books", Const.NSURI_BOOK);
		writer.writeNamespace("bk", Const.NSURI_BOOK);

		for (int i = 0; i < Const.NODE_COUNTS_S; ++i) {
			writer.writeStartElement("bk", "book", Const.NSURI_BOOK);
			writer.writeAttribute("id", String.valueOf((i + 1)), null, null);

			writer.writeStartElement("bk", "name", Const.NSURI_BOOK);
			String name = new StringBuilder("Name").append(i + i).toString();
			writer.writeValue(name);
			writer.writeEndElement("bk", "name123", Const.NSURI_BOOK);

			writer.writeStartElement("bk", "author", Const.NSURI_BOOK);
			writer.writeValue("author" + (i + 1));
			writer.writeEndElement("bk", "author", Const.NSURI_BOOK);

			writer.writeEndElement("bk", "book", Const.NSURI_BOOK);
		}
		writer.writeEndElement("bk", "books", Const.NSURI_BOOK);
		writer.close();
	}

	private static void streamWriter2(OutputStream out) throws IOException, XMLStreamException {
		File xsdFile = new File(Const.XSD_BOOK);
		XMLValidationSchemaFactory xsdFactory = XMLValidationSchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
		XMLValidationSchema schema = xsdFactory.createSchema(xsdFile);

		XMLOutputFactory factory = XMLOutputFactory2.newInstance();
		// factory.setProperty(name, value);

		XMLStreamWriter2 writer = (XMLStreamWriter2) factory.createXMLStreamWriter(out);

		writer.writeStartDocument("UTF-8", "1.0");

		writer.writeStartElement("bk", "books", Const.NSURI_BOOK);
		writer.writeNamespace("bk", Const.NSURI_BOOK);

		for (int i = 0; i < Const.NODE_COUNTS_L; ++i) {
			writer.writeStartElement("bk", "book", Const.NSURI_BOOK);
			writer.writeAttribute("id", String.valueOf(i + 1));

			writer.writeStartElement("bk", "name", Const.NSURI_BOOK);
			writer.writeCharacters("Name" + (i + 1));
			writer.writeEndElement();

			writer.writeStartElement("bk", "author", Const.NSURI_BOOK);
			writer.writeCharacters("author" + (i + 1));
			writer.writeEndElement();

			writer.writeEndElement();
		}
		writer.writeEndElement();
		writer.writeEndDocument();
		writer.close();
	}

	private static void eventWriter(OutputStream out) throws XMLStreamException {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		// factory.setProperty(name, value);

		XMLEventWriter writer = factory.createXMLEventWriter(out);
		XMLEventFactory ef = XMLEventFactory2.newInstance();

		StartDocument startDocument = ef.createStartDocument("UTF-8", "1.0");
		writer.add(startDocument);

		StartElement booksStartElement = ef.createStartElement("bk", Const.NSURI_BOOK, "books");
		writer.add(booksStartElement);
		writer.add(ef.createNamespace("bk", Const.NSURI_BOOK));

		for (int i = 0; i < Const.NODE_COUNTS_L; ++i) {
			writer.add(ef.createStartElement("bk", Const.NSURI_BOOK, "book"));
			writer.add(ef.createAttribute("id", String.valueOf(i + 1)));

			writer.add(ef.createStartElement("bk", Const.NSURI_BOOK, "name"));
			writer.add(ef.createCharacters("Name" + (i + 1)));
			writer.add(ef.createEndElement("bk", Const.NSURI_BOOK, "name"));

			writer.add(ef.createStartElement("bk", Const.NSURI_BOOK, "author"));
			writer.add(ef.createCharacters("author" + (i + 1)));
			writer.add(ef.createEndElement("bk", Const.NSURI_BOOK, "author"));

			writer.add(ef.createEndElement("bk", Const.NSURI_BOOK, "book"));
		}
		writer.add(ef.createEndElement("bk", Const.NSURI_BOOK, "books"));
		writer.add(ef.createEndDocument());
		writer.close();
	}

}

package comparation.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;

import constant.Const;

public class XbrliWriterBean {

	private FileOutputStream out;
	private XMLStreamWriter2 writer;
	private XMLPrettyFormatter formatter;

	public void init(String fileName) throws IOException, XMLStreamException {
		out = new FileOutputStream(fileName);
		XMLOutputFactory factory = XMLOutputFactory2.newInstance();
		writer = (XMLStreamWriter2) factory.createXMLStreamWriter(out);
		
		XMLValidationSchemaFactory xsdFactory = XMLValidationSchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
		File xsdUrl = new File(Const.XSD_BOOK);
		XMLValidationSchema schema = xsdFactory.createSchema(xsdUrl);
		
		writer.validateAgainst(schema);
		
		writer.writeStartDocument("UTF-8", "1.0");

		formatter = new DefaultPrettyFormatter(writer);
		// formatter = new DefaultPrettyFormatter(writer, "  ");
		// formatter = new EmptyPrettyFormatter(writer);
	}

	public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		formatter.writeStartElementIndention();
		writer.writeStartElement(prefix, localName, namespaceURI);
	}

	public void writeValue(String value) throws XMLStreamException {
		formatter.writeValueIndention();
		writer.writeCharacters(value.toCharArray(), 0, value.length());
	}

	public void writeEndElement() throws XMLStreamException {
		formatter.writeEndElementIndention();
		writer.writeEndElement();
	}

	public void writeAttribute(String localName, String value) throws XMLStreamException {
		writer.writeAttribute(localName, value);
	}

	public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
		writer.writeNamespace(prefix, namespaceURI);
	}

	public void close() throws XMLStreamException, IOException {
		writer.writeEndDocument();
		writer.close();
		out.close();
	}

}
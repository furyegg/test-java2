package comparation.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.validation.ValidationContext;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.codehaus.stax2.validation.XMLValidator;

import constant.Const;

public class XbrliWriterValidateBean {

	private FileOutputStream out;
	private XMLStreamWriter2 writer;
	private XMLValidator validator;
	private XMLPrettyFormatter formatter;

	public void init(String fileName) throws IOException, XMLStreamException {
		out = new FileOutputStream(fileName);
		XMLOutputFactory factory = XMLOutputFactory2.newInstance();
		writer = (XMLStreamWriter2) factory.createXMLStreamWriter(out);

		XMLValidationSchemaFactory xsdFactory = XMLValidationSchemaFactory
				.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
		File xsdUrl = new File(Const.XSD_BOOK);
		XMLValidationSchema schema = xsdFactory.createSchema(xsdUrl);

		ValidationContext valCtx = (ValidationContext) writer;
		validator = schema.createValidator(valCtx);

		// writer.validateAgainst(schema);

		writer.writeStartDocument("UTF-8", "1.0");

		formatter = new DefaultPrettyFormatter(writer);
		// formatter = new DefaultPrettyFormatter(writer, "  ");
		// formatter = new EmptyPrettyFormatter(writer);
	}

	public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		validator.validateElementStart(localName, namespaceURI, prefix);

		formatter.writeStartElementIndention();
		writer.writeStartElement(prefix, localName, namespaceURI);
	}

	public void writeValue(String value) throws XMLStreamException {
		validator.validateText(value, true);

		formatter.writeValueIndention();
		writer.writeCharacters(value.toCharArray(), 0, value.length());
	}

	public void writeEndElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
		validator.validateElementEnd(localName, namespaceURI, prefix);
//		validator.validateElementEnd(null, null, null);

		formatter.writeEndElementIndention();
		writer.writeEndElement();
	}

	public void writeAttribute(String localName, String value, String prefix, String namespaceURI)
			throws XMLStreamException {
		validator.validateAttribute(localName, namespaceURI, prefix, value);

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
package comparation.write;

import javax.xml.stream.XMLStreamException;

/**
 * XML pretty formatter.
 * 
 * @author lyhtbc@gmail.com
 */
public interface XMLPrettyFormatter {

	enum NodeType {
		ELEMENT, VALUE
	}

	String DEFAULT_INDENTION = "\t";

	void writeStartElementIndention() throws XMLStreamException;

	void writeValueIndention() throws XMLStreamException;

	void writeEndElementIndention() throws XMLStreamException;

}
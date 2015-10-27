package comparation.write;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;

public class EmptyPrettyFormatter implements XMLPrettyFormatter {

	public EmptyPrettyFormatter(XMLStreamWriter2 writer) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeStartElementIndention() throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeEndElementIndention() throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeValueIndention() throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

}

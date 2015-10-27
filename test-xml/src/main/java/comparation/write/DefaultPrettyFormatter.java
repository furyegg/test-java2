package comparation.write;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;

/**
 * Default XML pretty formatter.
 * <ol>
 * <li>Default intention is tab character "\t".</li>
 * <li>The value display in the same line with which element it belongs to.</li>
 * </ol>
 * 
 * @author lyhtbc@163.com
 */
public class DefaultPrettyFormatter implements XMLPrettyFormatter {

	private String indent = DEFAULT_INDENTION;
	private int currentLevel = 0;
	private NodeType nodeType;

	private XMLStreamWriter2 writer;

	public DefaultPrettyFormatter(XMLStreamWriter2 writer) {
		this.writer = writer;
	}

	public DefaultPrettyFormatter(XMLStreamWriter2 writer, String indent) {
		this.writer = writer;
		this.indent = indent;
	}

	@Override
	public void writeStartElementIndention() throws XMLStreamException {
		nodeType = NodeType.ELEMENT;

		writeLineBreaker();
		for (int i = 0; i < currentLevel; ++i) {
			writeIndention();
		}
		++currentLevel;
	}

	@Override
	public void writeValueIndention() throws XMLStreamException {
		nodeType = NodeType.VALUE;
	}

	@Override
	public void writeEndElementIndention() throws XMLStreamException {
		--currentLevel;

		if (nodeType == NodeType.ELEMENT) {
			writeLineBreaker();
			for (int i = 0; i < currentLevel; ++i) {
				writeIndention();
			}
		} else {
			// after write value, the node type should back to element
			nodeType = NodeType.ELEMENT;
		}
	}

	private void writeLineBreaker() throws XMLStreamException {
		writer.writeCharacters("\n");
	}

	private void writeIndention() throws XMLStreamException {
		writer.writeCharacters(indent.toCharArray(), 0, indent.length());
	}

}
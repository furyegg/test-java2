package comparation.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.codehaus.stax2.XMLEventReader2;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.xml.sax.SAXException;

import comparation.Timer;

import constant.Const;

public class WoodstoxStaxReader {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
		test2();
	}

	private static void test1() throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
		Timer t = new Timer();
		t.begin();

		File xmlFile = new File(Const.XML_BIG);
		FileInputStream fis = new FileInputStream(xmlFile);

		XMLInputFactory factory = XMLInputFactory2.newInstance();
		XMLStreamReader2 reader = (XMLStreamReader2) factory.createXMLStreamReader(fis, "UTF-8");

		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				StringBuilder res = new StringBuilder(reader.getLocalName()).append(reader.getNamespaceURI());
				break;
			case XMLStreamConstants.CHARACTERS:
				StringBuilder text = new StringBuilder(reader.getText());
				break;
			}
		}

		fis.close();
		t.end();
	}

	private static void test2() throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
		Timer t = new Timer();
		t.begin();

		File xmlFile = new File(Const.XML_BIG);
		FileInputStream fis = new FileInputStream(xmlFile);

		XMLInputFactory factory = XMLInputFactory2.newInstance();
		XMLEventReader2 reader = (XMLEventReader2) factory.createXMLEventReader(fis, "UTF-8");

		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			switch (event.getEventType()) {
			case XMLEvent.START_ELEMENT:
				StartElement startElement = event.asStartElement();
				StringBuilder res = new StringBuilder(startElement.getName().getLocalPart());
				break;
			case XMLEvent.CHARACTERS:
				Characters characters = event.asCharacters();
				StringBuilder text = new StringBuilder(characters.getData());
				break;
			}
		}

		t.end();
	}

}
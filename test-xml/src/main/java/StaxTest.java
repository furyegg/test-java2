import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import constant.Const;

public class StaxTest {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		FileInputStream input = new FileInputStream(Const.BOOK_XML);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader r = factory.createXMLStreamReader(input);
		try {
			int event = r.getEventType();
			while (true) {
				switch (event) {
				case XMLStreamConstants.START_DOCUMENT:
					System.out.println("Start Document.");
					break;
				case XMLStreamConstants.START_ELEMENT:
					System.out.println("Start Element: " + r.getName());
					for (int i = 0, n = r.getAttributeCount(); i < n; ++i)
						System.out.println("Attribute: " + r.getAttributeName(i) + "=" + r.getAttributeValue(i));

					break;
				case XMLStreamConstants.CHARACTERS:
					if (r.isWhiteSpace())
						break;

					System.out.println("Text: " + r.getText());
					break;
				case XMLStreamConstants.END_ELEMENT:
					System.out.println("End Element:" + r.getName());
					break;
				case XMLStreamConstants.END_DOCUMENT:
					System.out.println("End Document.");
					break;
				}

				if (!r.hasNext())
					break;

				event = r.next();
			}
		} finally {
			r.close();
		}
	}

}

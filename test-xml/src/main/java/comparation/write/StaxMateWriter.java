package comparation.write;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.codehaus.stax2.validation.XMLValidationSchemaFactory;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;

import constant.Const;

public class StaxMateWriter {

	public static void main(String[] args) throws XMLStreamException, MalformedURLException {
		// 1: need output factory
		SMOutputFactory outf = new SMOutputFactory(XMLOutputFactory.newInstance());
		
		URL xsdUrl = new URL("http://www.eba.europa.eu/eu/fr/xbrl/crr/fws/corep/its-2013-02/2013-12-01/mod/corep_con.xsd");
		XMLValidationSchemaFactory xsdFactory = XMLValidationSchemaFactory.newInstance(XMLValidationSchema.SCHEMA_ID_W3C_SCHEMA);
		XMLValidationSchema schema = xsdFactory.createSchema(xsdUrl);
		
		XMLStreamWriter2 writer = outf.createStax2Writer(new File(Const.PATH_XML + "empl.xml"));
		writer.validateAgainst(schema);
		
		// SMOutputDocument doc = outf.createOutputDocument(new File(Const.PATH_XML + "empl.xml"));
		
		// (optional) 3: enable indentation (note spaces after backslash!)
//		doc.setIndentation("\n  ", 1, 1);
//		
//		// 4. comment regarding generation time
//		doc.addComment(" generated: " + new java.util.Date().toString());
//		SMOutputElement empl = doc.addElement("employee");
//		empl.addAttribute(/* namespace */null, "id", 123);
//		SMOutputElement name = empl.addElement("name");
//		name.addElement("first").addCharacters("Tatu");
//		name.addElement("last").addCharacters("Saloranta");
//		// 10. close the document to close elements, flush output
//		doc.closeRoot();
	}

}

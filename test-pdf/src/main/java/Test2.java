import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.XfaForm;

public class Test2 {

	private static final String OUT_PATH = "src/main/resources/";

	public static void main(String[] args) throws Exception {
		String src = "liquiditybuffermonitor.pdf";
		String dest = OUT_PATH + "test3.xml";
		readXfa(src, dest);
	}

	public static void readXfa(String src, String dest) throws IOException, ParserConfigurationException, SAXException,
			TransformerFactoryConfigurationError, TransformerException {
		FileOutputStream os = new FileOutputStream(dest);
		PdfReader reader = new PdfReader(src);
		XfaForm xfa = new XfaForm(reader);
		org.w3c.dom.Document doc = xfa.getDomDocument();
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.transform(new DOMSource(doc), new StreamResult(os));
		reader.close();
	}

}

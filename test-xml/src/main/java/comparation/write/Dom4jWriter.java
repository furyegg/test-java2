package comparation.write;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import comparation.Timer;

import constant.Const;

public class Dom4jWriter {

	public static void main(String[] args) throws IOException {
		Timer t = new Timer();
		t.begin();

		FileOutputStream out = new FileOutputStream(Const.PATH_XML + "Dom4j.xml");
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, format);
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("books");
		writer.writeOpen(root);
		
		for (int i = 0; i < Const.NODE_COUNTS_L; ++i) {
			Element book = root.addElement("book").addAttribute("id", String.valueOf(i + 1));
			book.addElement("name").addText("Name" + (i + 1));
			book.addElement("author").addText("author" + (i + 1));
			
			writer.write(book);
		}
		writer.writeClose(root);
		writer.close();
		out.close();

		t.end();
	}

}
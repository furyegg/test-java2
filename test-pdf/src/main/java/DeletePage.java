import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.FileOutputStream;
import java.io.IOException;

public class DeletePage {

	public static void main(String[] args) throws IOException, DocumentException {
		String src = "d:/1.pdf";
		String target = "d:/2.pdf";

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target));
		document.open();

		PdfReader reader = new PdfReader(src);
		int n = reader.getNumberOfPages();
		PdfImportedPage page;
		// Go through all pages
		for (int i = 1; i <= n; i++) {
			String content = PdfTextExtractor.getTextFromPage(reader, i);
			if (content.contains("Visual C++(VC/MFC)")) {
				// System.out.println(content);
				continue;
			} else {
				page = writer.getImportedPage(reader, i);
				Image instance = Image.getInstance(page);
				document.add(instance);
			}
		}
		document.close();
	}

}
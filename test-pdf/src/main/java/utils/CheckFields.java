package utils;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public class CheckFields {

	private static final String OUT_PATH = "src/main/resources/";
	
	public static void main(String[] args) throws Exception {
		String pdf = "cashsurplusdeficitreportall.pdf";
		URL url = Thread.currentThread().getContextClassLoader().getResource(pdf);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfReader reader = new PdfReader(url);
		PdfCopyFields copy = new PdfCopyFields(baos);
		copy.addDocument(reader, "5");
		copy.close();
		
		FileOutputStream fout = new FileOutputStream(OUT_PATH + "checkfields.pdf");
		PdfReader reader2 = new PdfReader(baos.toByteArray());
		PdfStamper stamper = new PdfStamper(reader2, fout);

		List<String> fields = new ArrayList<String>();
		AcroFields form = stamper.getAcroFields();
		for (String key : form.getFields().keySet()) {
			fields.add(getFieldNameFromXfaKey(key));
		}
		Collections.sort(fields);
		
		int n = 0;
		for (String field : fields) {
			++n;
			System.out.printf("%3d: %s\n", n, field);
		}
	}
	
	public static String getFieldNameFromXfaKey(String key) {
		int beginIndex = key.lastIndexOf(".") + 1;
		int endIndex = key.lastIndexOf("[");
		return key.substring(beginIndex, endIndex);
	}

}

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

public class Temp {

	private static final String XPATH_FIELD = ".//*[name()='field' and @name]";
	private static final String OUT_PATH = "src/main/resources/";
	private static Map<String, String> FIELD_MAP = new HashMap<String, String>();
	static {
		FIELD_MAP.put("CSDR02C01", new Date().toString());
		FIELD_MAP.put("CSDR03C01", "TWO WEEK\nLOW POINT");
		FIELD_MAP.put("CSDR04C01", String.valueOf(1.23));

		FIELD_MAP.put("CSDR02C03", new Date().toString());
		FIELD_MAP.put("CSDR03C03", "THREE MONTH\n LOW POINT");
		FIELD_MAP.put("CSDR04C03", String.valueOf(4.56));
	}

	public static void main(String[] args) throws Exception {
		Temp t = new Temp();
	}

	private int pageSize = 5;
	private PdfReader pdfReader;
	private URL url;

	public Temp() throws Exception {
		// cashsurplusdeficitreport.pdf
		url = Thread.currentThread().getContextClassLoader().getResource("cashsurplusdeficitreport.pdf");
		pdfReader = new PdfReader(new RandomAccessFileOrArray(url), null);

		FileOutputStream out = new FileOutputStream(OUT_PATH + "temp.pdf");

		int totalCount = 6;
		int pageCount = getPageCount(totalCount, pageSize);
		int lastPageSize = totalCount - (pageCount - 1) * pageSize;
		int page;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfCopyFields copy = new PdfCopyFields(out);

		// add all pages except last page
		for (page = 0; page < pageCount - 1; ++page) {
			copy.addDocument(generateTemplatePage(page, pageSize));
		}
		// add last page
		++page;
		copy.addDocument(generateTemplatePage(page, lastPageSize));
		copy.close();

		// PdfStamper stamper = new PdfStamper(reader2, out);
		// AcroFields form = stamper.getAcroFields();

		// for (String key : )

		// List<String> nameList = getNameList(form);
		// for (String name : nameList) {
		// String key = form.getTranslatedFieldName(name);
		// System.out.println(key);
		// form.setField(key, FIELD_MAP.get(name));
		// }

		// stamper.close();
	}

	private PdfReader generateTemplatePage(int page, int currentPageSize) throws Exception {
		int totalCols = pageSize * (page - 1);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(getOriginalPage(currentPageSize), baos);
		// renameFields(stamper.getAcroFields(), totalCols);
		updateFields(stamper.getAcroFields());
		stamper.close();

		return new PdfReader(baos.toByteArray());
	}

	private PdfReader getOriginalPage(int pageNumber) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfCopyFields copy = new PdfCopyFields(baos);
		
		// PdfReader reader = new PdfReader(new RandomAccessFileOrArray(url), null);
		pdfReader = new PdfReader(new RandomAccessFileOrArray(url), null);
		copy.addDocument(pdfReader, String.valueOf(pageNumber));
		copy.close();

		return new PdfReader(baos.toByteArray());
	}

	private void updateFields(AcroFields form) throws Exception {
		for (String key : FIELD_MAP.keySet()) {
			String field = form.getTranslatedFieldName(key);
			form.setField(field, FIELD_MAP.get(key));
		}
	}

	private int getPageCount(int total, int pageSize) {
		return total / pageSize + (total % pageSize == 0 ? 0 : 1);
	}

}

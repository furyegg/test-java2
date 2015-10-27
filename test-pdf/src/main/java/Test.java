//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.net.URL;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.xpath.XPathAPI;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import com.itextpdf.text.pdf.AcroFields;
//import com.itextpdf.text.pdf.PdfCopyForms;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//
//public class Test {
//
//	private static final String XPATH_FIELD = ".//*[name()='field' and @name]";
//	private static Map<String, String> FIELD_MAP = new HashMap<String, String>();
//	static {
//		FIELD_MAP.put("CSDR02C01_1", new Date().toString());
//		FIELD_MAP.put("CSDR03C01_1", "TWO WEEKS LOW POINT");
//		FIELD_MAP.put("CSDR04C01_1", String.valueOf(1.23));
//	}
//
//	public static void main(String[] args) throws Exception {
//		// liquiditybuffermonitor.pdf
//		URL url = Thread.currentThread().getContextClassLoader().getResource("liquiditybuffermonitor.pdf");
//		// PdfReader pdfReader = new PdfReader(new RandomAccessFileOrArray(url), null);
//		PdfReader reader = new PdfReader(url);
//
//		FileOutputStream os = new FileOutputStream("src/main/resources/result.pdf");
//
////		System.out.println(reader2.getNumberOfPages());
////		PdfStamper stamper = new PdfStamper(reader2, os);
////		AcroFields form = stamper.getAcroFields();
////		System.out.println(form.getFields().size());
//
////		String key = "form1[0].#subform[0].Table1[0].Row3[1].CSDR03C01[0]";
////		String newKey = "form1[0].#subform[0].Table1[0].Row3[1].CSDR03C01_1[0]";
////		boolean res = acroFields.renameField(key, newKey);
////		if (res) {
////			System.out.println("new kye: " + newKey);
////			acroFields.setField(newKey, "456");
////		}
//
////		PdfStamper stamper = new PdfStamper(reader, os);
////		AcroFields form = stamper.getAcroFields();
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		// PdfCopyFields copy = new PdfCopyFields(baos);
//		PdfCopyForms copy = new PdfCopyForms(baos);
//		copy.addDocument(reader, "1");
//		copy.close();
//
//		PdfReader reader2 = new PdfReader(baos.toByteArray());
//		PdfStamper stamper = new PdfStamper(reader, os);
//		AcroFields form = stamper.getAcroFields();
//
//		Document doc = form.getXfa().getDomDocument();
//        NodeList fieldNodeList = XPathAPI.selectNodeList(doc, XPATH_FIELD);
//        int length = fieldNodeList.getLength();
//        for (int i = 0; i < length; i++) {
//            Node node = fieldNodeList.item(i);
//            String name = node.getAttributes().getNamedItem("name").getNodeValue();
//            String formatValue = null;
//            NodeList children = node.getChildNodes();
//            int childrenLength = children.getLength();
//            Node formatNode = null;
//            for (int j = 0; j < childrenLength; j++) {
//                if (children.item(j).getNodeName().equals("format")) {
//                    formatNode = children.item(j);
//                }
//            }
//            if (formatNode != null) {
//                formatValue = formatNode.getChildNodes().item(0).getTextContent();
//                System.out.println(formatValue);
//            }
//
//            // keyFieldMap.put(name, field);
//        }
//
////		List<String> names = new ArrayList<String>();
////		Document doc = form.getXfa().getDomDocument();
////
////		NodeList fieldNodeList = XPathAPI.selectNodeList(doc, XPATH_FIELD);
////		int length = fieldNodeList.getLength();
////		for (int i = 0; i < length; i++) {
////			Node node = fieldNodeList.item(i);
////			String name = node.getAttributes().getNamedItem("name").getNodeValue();
////			names.add(name);
////		}
////		System.out.println(names.size());
////
////		for (String name : names) {
////			// acroFields.setField(name, FIELD_MAP.get(name));
////			String newName = "L" + name;
////			boolean res = acroFields.renameField(name, newName);
////			if (res) {
////				System.out.println("new name: " + newName);
////			} else {
////				System.out.println("old name: " + name);
////			}
////		}
//
// 		stamper.close();
//		reader.close();
//	}
//}

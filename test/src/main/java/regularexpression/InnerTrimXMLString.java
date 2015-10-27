package regularexpression;

public class InnerTrimXMLString {

	private static final String XML = " <message> </ns2:formInstanceExportRequest>  </ns2:body> </message> ";
	
	public static void main(String[] args) {
		String regex = "^.*(<.*?>).*(<.*?>).*$";
		String[] matchedGroup = RegExUtils.findMatchedGroup(regex, XML);
		System.out.print(matchedGroup.length);
		for (String s : matchedGroup) {
			System.out.print(s);
		}
	}
	
}
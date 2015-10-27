package regularexpression;

import java.util.regex.Pattern;

public class InnerTrimString {

	private static final String XML = "   abc deg  sadf ";
	
	public static void main(String[] args) {
		String regex1 = "^.*\\w+\\s*.*$";
		String regex2 = "^\\s*(\\w+?\\s*\\w+?)\\s*$";
		System.out.println(Pattern.matches(regex1, XML));
		String[] matchedGroup = RegExUtils.findMatchedGroup(regex2, XML);
		System.out.println(matchedGroup.length);
		for (String s : matchedGroup) {
			System.out.print(s);
		}
	}
	
}
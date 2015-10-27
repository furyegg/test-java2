package regularexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quantifiers {

	public static void main(String[] args) {
		String matcher = "abcxfooxxxxxxfoo";
		match("(.*foo)", matcher);
		match("(.*?foo)", matcher);
		match("(x*+foo)", matcher);
	}
	
	private static void match(String regex, String matcher) {
		Matcher m = Pattern.compile(regex).matcher(matcher);
		if (m.find()) {
			System.out.println("find: " + m.groupCount());
			for (int i = 1; i <= m.groupCount(); ++i) {
				System.out.println(m.group(i) + ": " + m.start(i) + "," + m.end(i));
			}
		} else {
			System.out.println("Not matched");
		}
		System.out.println();
	}
	
}

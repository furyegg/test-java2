package regularexpression;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) throws Exception {
//		System.out.println("the number 12345678 is large".replaceAll("(?<=\\d)(?=(?:\\d\\d\\d)+(?!\\d))", ","));
		String s = "a123s456d789fa%";
		Matcher m = Pattern.compile("^[a-z]*(?:(\\d++)[a-z]*)*(%)$").matcher(s);
		System.out.println(m.find());
		System.out.println(m.groupCount());
		System.out.println(m.group(1));
		System.out.println(m.start() + ", " + m.end());

//		wordBoundary();
	}

	public static void wordBoundary() {
		String s = "see Jeffs book".replaceAll("\\bJeffs\\b", "Jeff's");
		System.out.println(s);

		System.out.println(Pattern.matches(".*\\bdog\\b.*", "The dog plays in the yard"));

		Pattern p = Pattern.compile("\\bdog\\b");
		Matcher m = p.matcher("The dog plays in the yard");
		if (m.find()) {
			System.out.println(m.start() + ", " + m.end());
		}
	}

}

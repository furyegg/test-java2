package regularexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberTest {

	/**
	 * Check the value is number or not.
	 * <ul>
	 * <li>120 -> true</li>
	 * <li>12.00 -> true</li>
	 * <li>12.01 -> true</li>
	 * <li>0.12 -> true</li>
	 * <li>.12 -> true</li>
	 * <li>00.12 -> true</li>
	 * </ul>
	 * Otherwise is false.
	 */
	public static void main(String[] args) throws Exception {
		String[] matchers = new String[] {
			"-120",
			"210",
			"12.00",
			"12.01",
			"0.12",
			".12",
			"-.12",
			"00.12",
			"12.",
			"12.a",
			"a2.",
			"abc"
		};
		
		for (String matcher : matchers) {
			// find(matcher);
			match(matcher);
		}
		
		// test();
		// System.out.println(new String[0].length);
	}
	
	private static void find(String matcher) {
		String regex = "^[-\\d\\]*\\.*\\d+$";
		String[] matchedGroup = RegExUtils.findMatchedGroup(regex, matcher);
		System.out.println(matchedGroup.length);
		System.out.println(matchedGroup[0]);
		System.out.println("----------------");
	}
	
	private static void match(String matcher) {
		String regex = "^(-?|\\d*)?\\.?\\d+$";
		System.out.print(matcher + ": ");
		System.out.println(Pattern.matches(regex, matcher));
	}

	private static void test() {
		String regex = "^(\\d+?)(\\D*?)$";
		String matcher = "55px";
		Matcher m = Pattern.compile(regex).matcher(matcher);
		if (m.find()) {
			System.out.println("find: " + m.groupCount());
			for (int i = 1; i <= m.groupCount(); ++i) {
				System.out.println(m.group(i));
			}
		}
	}

}

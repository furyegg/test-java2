package regularexpression;

import org.apache.commons.lang3.StringUtils;

public class BackReference {

	public static void main(String[] args) throws Exception {
		String s = "12.375000000123";
		String[] matchers = new String[]{
				"12.375000000123",
				"12.050000"
		};

		for (String matcher : matchers) {
			// replace(matcher);
			find(matcher);
		}
	}

	private static void replace(String matcher) {
		String regex = "(\\.\\d\\d[1-9]?)\\d*";
		System.out.println(matcher.replace(regex, "###"));
	}

	private static void find(String matcher) {
		String regex = "^(\\d*\\.\\d*?)0++$";
		String[] matchedGroup = RegExUtils.findMatchedGroup(regex, matcher);
		System.out.println(matchedGroup.length);
		printArray(matchedGroup);
		System.out.println("----------------");
	}

	private static void printArray(String[] matchedGroup) {
		System.out.println(StringUtils.join(matchedGroup, ", "));
	}

}
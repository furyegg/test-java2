package regularexpression;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupTest {

	public static void main(String[] args) throws Exception {
		String[] matchers = new String[]{
				"IF(OR(AND(LEN(Fiduciary1)>0,LEN(Fiduciary2)>0),AND(LEN(LENABC)>0,LEN(Fiduciary3)>0),AND(LEN(Fiduciary2)>0,LEN(Fiduciary3)>0)),TRUE,FALSE)",
				"#LEN(Fiduciary1)>0,LEN(Fiduciary2)>0),AND(LEN(LENABC)>0,LEN(Fiduciary3)>0),AND(LEN(Fiduciary2)>0,LEN(Fiduciary3)>0)),TRUE,FALSE)",
				"IF(OR(AND(LEN(Fiduciary1)>0,LEN(Fiduciary2)>0),AND(LEN(LENABC)>0,LEN(Fiduciary3)>0),AND(LEN(Fiduciary2)>0,LEN",
				"LEN(Fiduciary2)>0,LEN"
		};



		for (String matcher : matchers) {
//			find(matcher);
//			match(matcher);
			split(matcher);
		}

		// test();
		// System.out.println(new String[0].length);
	}

	private static void find(String matcher) {
		String regex = "^\\W*(LEN*?)\\W+$";
		String[] matchedGroup = RegExUtils.findMatchedGroup(regex, matcher);
		System.out.println(matchedGroup.length);
		printArray(matchedGroup);
		System.out.println("----------------");
	}

	private static void match(String matcher) {
//		String regex = "^\\([\\S+&&[^\\(\\)]]\\)$";
		String regex = "\\W?LEN\\W{1}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(matcher);

		System.out.print(matcher + " --- ");
		System.out.println(m.matches());
		while (m.find()) {
			System.out.println(m.start() + ", " + m.end());
		}
		System.out.println();
	}

	private static void split(String matcher) {
		String regex = "\\W?LEN\\W{1}";
		Pattern p = Pattern.compile(regex);
//		String[] elements = p.split(matcher);
		String[] elements = split(p, matcher);
		System.out.println(elements.length);
		System.out.println(StringUtils.join(elements, ", "));
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

	private static void printArray(String[] matchedGroup) {
		System.out.println(StringUtils.join(matchedGroup, ", "));
	}

	public static String[] split(Pattern p, CharSequence input, int limit) {
		int index = 0;
		boolean matchLimited = limit > 0;
		ArrayList<String> matchList = new ArrayList<>();
		Matcher m = p.matcher(input);

		// Add segments before each match found
		while(m.find()) {
			if (!matchLimited || matchList.size() < limit - 1) {
				String match = input.subSequence(index, m.start()).toString();
				matchList.add(match);
				index = m.end();
			} else if (matchList.size() == limit - 1) { // last one
				String match = input.subSequence(index,
						input.length()).toString();
				matchList.add(match);
				index = m.end();
			}
		}

		// If no match was found, return this
		if (index == 0)
			return new String[] {input.toString()};

		// Add remaining segment
		if (!matchLimited || matchList.size() < limit)
			matchList.add(input.subSequence(index, input.length()).toString());

		// Construct result
		int resultSize = matchList.size();
		if (limit == 0)
			while (resultSize > 0 && matchList.get(resultSize-1).equals(""))
				resultSize--;
		String[] result = new String[resultSize];
		return matchList.subList(0, resultSize).toArray(result);
	}

	public static String[] split(Pattern p, CharSequence input) {
		return split(p, input, 0);
	}

}

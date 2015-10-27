package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Regular expression utilities.
 */
public abstract class RegExUtils {

	/**
	 * Find matched string array.<br/>
	 * <b>Note:</b><br/>
	 * <ul>
	 * <li><b>Excluded matcher itself.</b> First element is first matched string, it's <b>not</b> matcher itself.</li>
	 * </ul>
	 */
	public static String[] findMatchedGroup(String regex, String matcher, boolean ignoreBlank) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(matcher);
		if (m.find()) {
			List<String> list = new ArrayList<String>(m.groupCount());
			for (int i = 1; i <= m.groupCount(); ++i) {
				String item = m.group(i);
				if (StringUtils.isBlank(item) && ignoreBlank) {
					continue;
				}
				list.add(item);
			}
			return list.toArray(new String[0]);
		} else {
			return new String[0];
		}
	}

	/**
	 * Find matched string array.<br/>
	 * <b>Note:</b><br/>
	 * <ul>
	 * <li><b>Excluded matcher itself.</b> First element is first matched string, it's <b>not</b> matcher itself.</li>
	 * <li>Ignore empty element.</li>
	 * </ul>
	 * 
	 * @see #findMatchedGroup(String, String, boolean)
	 */
	public static String[] findMatchedGroup(String regex, String matcher) {
		return findMatchedGroup(regex, matcher, true);
	}

	/**
	 * Insert a string before or after the specified part.
	 */
	public static String insertString(String origin, String regex, String insertString, boolean insertAtFront) {
		String groupRegex = String.format("^(.*?)(%s)(.*?)$", regex);
		String[] matchedGroup = findMatchedGroup(groupRegex, origin);
		StringBuilder result = new StringBuilder();
		for (String group : matchedGroup) {
			if (Pattern.matches(regex, group)) {
				if (insertAtFront) {
					result.append(insertString).append(group);
				} else {
					result.append(group).append(insertString);
				}
			} else {
				result.append(group);
			}
		}
		return result.toString();
	}

	/**
	 * Build regular expression from string. Only support begin and end wildcard characters.<br/>
	 * For example:
	 * <ul>
	 * <li>GB* -> GB.*</li>
	 * <li>GB*** -> GB.*</li>
	 * <li>GB*?* -> GB.*</li>
	 * <li>?GB* -> \S?GB.*</li>
	 * <li>?*GB* -> .*GB.*</li>
	 * <li>A? -> A\S?</li>
	 * <li>A?? -> A\S?\S?</li>
	 * </ul>
	 */
	public static String buildRegEx(String instCodeMatch) {
		String regex = instCodeMatch.replaceAll("\\?*\\*+\\?*\\**", ".*");
		regex = regex.replaceAll("\\?", "\\\\S?");
		return regex;
	}

}
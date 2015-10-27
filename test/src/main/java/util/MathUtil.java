package util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Mathematical utilities.
 */
public class MathUtil {

	/**
	 * Check a double equals to zero or not.
	 */
	public static boolean equalsToZero(Double value) {
		return value < 0.00000001 ? true : false;
	}

	/**
	 * Remove zero at end of fractional part.
	 * <ul>
	 * <li>0.1200 -> 0.12</li>
	 * <li>0.0000 -> 0</li>
	 * <li>12000 -> 12000</li>
	 * </ul>
	 */
	public static String removeRedundantZero(String value) {
		String regex = "^(-*\\d*+)(\\.0*?[\\d&&[^0]]*?)(0+?)$";
		String[] matchedGroup = RegExUtils.findMatchedGroup(regex, value);
		if (matchedGroup.length > 0) {
			StringBuilder sb = new StringBuilder(matchedGroup[0]);

			// if second group length > 1 (like ".123")
			// if second group length = 1 (just "."), ignore this dot.
			if (matchedGroup[1].length() > 1) {
				sb.append(matchedGroup[1]);
			}
			return sb.toString();
		}
		return value;
	}

	/**
	 * Check the value is number or not.
	 * <ul>
	 * <li>(-)120 -> true</li>
	 * <li>(-)12.00 -> true</li>
	 * <li>(-)12.01 -> true</li>
	 * <li>(-)0.12 -> true</li>
	 * <li>(-).12 -> true</li>
	 * <li>(-)00.12 -> true</li>
	 * <li>(-)12. -> false</li>
	 * </ul>
	 * Otherwise is false.
	 */
	public static boolean isNumber(Object value) {
		if (value == null) {
			return false;
		}

		String valStr = value.toString();
		if (StringUtils.isBlank(valStr)) {
			return false;
		}

		String regex = "^(-?|\\d*)?\\.?\\d+$";
		return Pattern.matches(regex, valStr);
	}

}
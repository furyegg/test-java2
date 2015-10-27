package regularexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceAll {

	public static void main(String[] args) throws Exception {
//		System.out.println("the number 12345678 is large".replaceAll("(?<=\\d)(?=(?:\\d\\d\\d)+(?!\\d))", ","));

		System.out.println("47380945122.96260150000".replaceAll("^(-?\\d*\\.\\d+?)0++$", "$1"));
		System.out.println("47380945122.96260150000".replaceAll("(-?\\d*+\\.\\d*[1-9])0++", "$1"));
//		System.out.println("0.01020".replaceAll("^(-?\\d*\\.\\d*?)0++$", "$1"));

		System.out.println("47380945122.96260150000".replaceAll("^(?:(-?[1-9]\\d*)\\.|(-?\\d*+\\.\\d+?))0++$", "$1$2"));
		System.out.println("47380945122.00000000000".replaceAll("^(?:(-?[1-9]\\d*)\\.|(-?\\d*+\\.\\d+?))0++$", "$1$2"));

		System.out.println("--------------------------------------------");

		Pattern pattern = Pattern.compile("(\\d*+\\.\\d*[1-9])0++$");
		String value = "47380945122.96260150000";
		Matcher m = pattern.matcher(value);
		System.out.println(m.find());
		System.out.println(m.groupCount());
		System.out.println(m.group(0));
		System.out.println(m.group(1));

//		m.reset();
//		System.out.println(value.length() + ", " + m.find() + ", " + m.start() + ", " + m.end());
	}

}
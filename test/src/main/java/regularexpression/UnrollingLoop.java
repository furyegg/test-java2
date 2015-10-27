package regularexpression;

import util.Timer;

import java.util.regex.Pattern;

public class UnrollingLoop {

	public static void main(String[] args) {
		String[] matchers = new String[]{"\"You need a 2\\\"3\\\" photo.\"",
				"\"You need a 2\\\"3\\\" photo asdfasdfasdfsdafsd You need a 2\\\"3\\\" photo You need a 2\\\"3\\\" photo.\"",
				"\"\\y\"", "\"You need a 2\\\"3\\\""};

		Timer timer = new Timer();
		timer.begin();
		for (String matcher : matchers) {
			loop(matcher);
		}
		timer.end();

		System.out.println("-----------------------------------------");

		timer.begin();
		for (String matcher : matchers) {
			unRollingLoop(matcher);
		}
		timer.end();
	}

	private static void unRollingLoop(String matcher) {
		Pattern p = Pattern.compile("\"[^\\\\\"]*(?:\\\\.[^\\\\\"]*)*\"");
		System.out.println(matcher + " -> " + p.matcher(matcher).matches());
	}

	private static void loop(String matcher) {
		Pattern p = Pattern.compile("\"(?:\\\\.|[^\\\\\"]+)*\"");
		System.out.println(matcher + " -> " + p.matcher(matcher).matches());
	}

}
package valueparser;

import org.apache.commons.lang3.time.DateUtils;
import org.w3c.dom.Node;

import java.util.Date;

public class Client {
	
	static class ValueParseException extends Exception {
		public ValueParseException(String msg, Throwable t) {
			super(msg, t);
		}
	}
	
	static class ValueParser {
		public static Object parse(String value, Class clazz) throws ValueParseException {
			if (clazz == Integer.class) {
				return asInt(value);
			} else if (clazz == Date.class) {
				return asDate(value);
			} else {
				throw new IllegalArgumentException("Unsupported data type: " + clazz);
			}
		}
		
		private static Integer asInt(String value) throws ValueParseException {
			try {
				return Integer.valueOf(value);
			} catch (Exception e) {
				throw new ValueParseException(String.format("Failed to parse %s as Integer", value), e);
			}
		}
		
		private static Date asDate(String value) throws ValueParseException {
			String format = "yyyy-MM-dd";
			try {
				return DateUtils.parseDate(value, format);
			} catch (Exception e) {
				throw new ValueParseException(String.format("Failed to parse %s as Date with format %s", value, format), e);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			setInt(parseValue(null, "/aaa", 1));
			setDate(parseValue(null, "/bbb", new Date()));
		} catch (ValueParseException e) {
		
		}
	}
	
	public static <T> T parseValue(Node node, String xpath, T defaultValue) throws ValueParseException {
		String value = "";
		return (T) ValueParser.parse(value, defaultValue.getClass());
	}
	
	public static void setInt(Integer value) {
	
	}
	
	public static void setDate(Date value) {
	
	}
}

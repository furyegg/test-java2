package enumeration;

import java.util.EnumSet;

public class PrintEnumList {

	public static void main(String[] args) {
		getEnumMemberArrary();
	}

	public static String getAllEnumAsString() {
		return "";
	}

	public static <T extends Enum> String[] getEnumMemberArrary() {
		EnumSet<FunctionType> enumSet = EnumSet.allOf(FunctionType.class);
		System.out.println(enumSet.toString());
		return new String[0];
	}

}
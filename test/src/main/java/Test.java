import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Test {

	static enum Color {
		RED, GREEN, BLUE;
	}

	public List<String> list = new ArrayList<>();

	// System.out.println(list);
	public static void main(String[] args) throws IOException {
		Map<Integer, String> map = Maps.newTreeMap();
		map.put(1, "a");
		map.put(3, "b");
		map.put(4, "c");
		map.put(2, "d");
		map.values().forEach(System.out::println);
	}

	public static void test(Object... params) {
		System.out.println(params.length);
	}

}
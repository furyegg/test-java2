import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

	static enum Color {
		RED, GREEN, BLUE;
	}

	public List<String> list = new ArrayList<>();

	// System.out.println(list);
	public static void main(String[] args) throws IOException {
		Test t = new Test();
		List<String> list = arrayListIfNull(t.getList());
//		List<String> list = t.getList();
//		if (list == null) {
//			list = new ArrayList<>();
//		}
		list.add("a");
		System.out.println(t.getList());
	}

	public static <T> Set<T> hashSetIfNull(Set<T> set) {
		if (set == null) {
			set = Sets.newHashSet();
		}
		return set;
	}

	public static <T extends Comparable> Set<T> treeSetIfNull(Set<T> set) {
		if (set == null) {
			set = Sets.newTreeSet();
		}
		return set;
	}

	public static <T> List<T> arrayListIfNull(List<T> list) {
		if (list == null) {
			list = Lists.newArrayList();
		}
		return list;
	}

	public static <T> List<T> linkedListIfNull(List<T> list) {
		if (list == null) {
			list = Lists.newLinkedList();
		}
		return list;
	}

	public List<String> getList() {
		return list;
	}
}
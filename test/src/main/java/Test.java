import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Test {

	static enum Color {
		RED, GREEN, BLUE;
	}

	public List<String> list = new ArrayList<>();

	static class Food {
	}

	static class Fruit extends Food {
	}

	static class Apple extends Fruit {
	}

	static class RedApple extends Apple {
	}

	static class SortedList<T extends Comparable<? super T>> extends LinkedList<T> {

	}

	// System.out.println(list);
	public static void main(String[] args) throws IOException {
		List<? super Fruit> flist = Lists.newArrayList();
		flist.add(new Fruit());
		flist.add(new Apple());
		flist.add(new RedApple());

		List<? extends Date> list = new ArrayList<Date>();
		// list.add(new Date());

		Stream<? extends Date> stream = list.stream();

		System.out.println(flist.size());

		SortedList<java.sql.Date> dateList = new SortedList<java.sql.Date>();
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
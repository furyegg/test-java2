package test.hbase;

import one.util.streamex.StreamEx;

public class Test {
	public static void main(String[] args) {
		int[] arr = {1,2,3};
		System.out.println(StreamEx.empty().append(arr).toList().toString());
	}
}

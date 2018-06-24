package collection;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import one.util.streamex.StreamEx;

public class Calculcate {
	public static void main(String[] args) {
		Calculcate c = new Calculcate();
		
		List<Integer> numbers = List.ofAll(Stream.range(1, 101).toJavaList());
		int init = 1000;
		
		int res1 = c.sum1(init, numbers);
		System.out.println(res1);
		
		int res2 = c.sum2(init, numbers);
		System.out.println(res2);
	}
	
	private int sum1(int init, List<Integer> numbers) {
		return sum(numbers, init);
	}
	
	private int sum(List<Integer> numbers, int result) {
		if (numbers.isEmpty()) {
			return result;
		} else {
			return sum(numbers.tail(), result + numbers.head());
		}
	}
	
	private int sum2(int init, List<Integer> numbers) {
		return StreamEx.of(numbers.toJavaList()).foldLeft(init, (e1, e2) -> e1 + e2);
	}
}

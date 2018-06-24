package collection;

public class ListTest {
	public static void main(String[] args) {
		List2<Integer> list1 = new Empty<Integer>().push(3).push(2).push(1);
		System.out.println("list1: " + list1.getContent());
		
		List2<Integer> list2 = new List2(6).push(5).push(4);
		System.out.println("list2: " + list2.getContent());
		
		System.out.println("list3: " + list1.union(list2).getContent());
	}
}
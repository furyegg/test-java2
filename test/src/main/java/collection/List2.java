package collection;

public class List2<T> implements ListLike<T> {
	
	private T head;
	private ListLike<T> tail;
	
	List2(T item, ListLike<T> list) {
		this.head = item;
		this.tail = list;
	}
	
	public List2(T item) {
		head = item;
		tail = new Empty<>();
	}
	
	@Override
	public List2<T> push(T item) {
		return new List2(item, this);
	}
	
	@Override
	public T head() {
		return head;
	}
	
	@Override
	public ListLike<T> tail() {
		return tail;
	}
	
	@Override
	public T get(int i) {
		if (i == 0) {
			return head;
		} else {
			return tail.get(i - 1);
		}
	}
	
	@Override
	public ListLike<T> union(ListLike<T> list) {
		if (tail.isEmpty()) {
			return new List2(head, list);
		} else {
			return new List2(head, tail.union(list));
		}
	}
}
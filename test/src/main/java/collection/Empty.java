package collection;

public class Empty<T> implements ListLike<T> {
	@Override
	public List2<T> push(T item) {
		return new List2(item, this);
	}
	
	@Override
	public T head() {
		throw new IllegalStateException("Unable get head from empty list");
	}
	
	@Override
	public ListLike<T> tail() {
		throw new IllegalStateException("Unable get tail from empty list");
	}
	
	@Override
	public T get(int i) {
		throw new IllegalStateException("Unable get item from empty list");
	}
	
	@Override
	public ListLike<T> union(ListLike<T> list) {
		return list;
	}
	
}
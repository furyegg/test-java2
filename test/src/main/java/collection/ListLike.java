package collection;

public interface ListLike<T> {
	
	ListLike<T> push(T item);
	
	T head();
	
	ListLike<T> tail();
	
	T get(int i);
	
	ListLike<T> union(ListLike<T> list);
	
	default boolean isEmpty() {
		return getClass() == Empty.class;
	}
	
	default String getContent() {
		StringBuilder sb = new StringBuilder();
		if (getClass() == Empty.class) {
			sb.append("Empty");
		} else {
			sb.append(head())
					.append(" :: ")
					.append(tail().getContent());
		}
		return sb.toString();
	}
}
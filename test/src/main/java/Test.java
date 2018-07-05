import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Optional;

public class Test {
	static class A {}

	static class B extends A {}

	static A a;

    public static void main(String[] args) {
		List<String> list = Lists.newArrayList();
		list.add(null);
    
        boolean isNull = list.stream().anyMatch(v -> v == null);
        System.out.println(isNull);
    
        System.out.println(StreamEx.of(list)
                .map(v -> Optional.ofNullable(v))
                .findFirst(v -> !v.isPresent()));
    }

    private static List<A> get() {
		List<A> l = Lists.newArrayList();
		l.add(new B());
		return l;
    }
}
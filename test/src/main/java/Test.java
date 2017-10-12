import com.google.common.collect.Lists;

import java.util.List;

public class Test {
	static class A {}

	static class B extends A {}

    public static void main(String[] args) {

    }

    private static List<A> get() {
		List<A> l = Lists.newArrayList();
		l.add(new B());
		return l;
    }
}
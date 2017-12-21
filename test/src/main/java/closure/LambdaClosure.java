package closure;

import one.util.streamex.StreamEx;

public class LambdaClosure {
	private static class A {
		public String getName() {
			return "A";
		}
	}
	public static void main(String[] args) {
		A a = new A();
		StreamEx.of(1,2,3).map(i -> a.getName() + i);
	}
}

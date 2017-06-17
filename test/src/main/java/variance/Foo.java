package variance;

/**
 * Created by Administrator on 2016/7/29.
 */
public class Foo {
	public <T extends A> String test(T t) {
		return t.getClass().toString();
	}
}
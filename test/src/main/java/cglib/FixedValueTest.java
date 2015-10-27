package cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

public class FixedValueTest {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback(new FixedValue() {
			@Override
			public Object loadObject() throws Exception {
				System.out.println("inner proxy");
				return "Hello cglib!";
			}
		});
		SampleClass proxy = (SampleClass) enhancer.create();
		proxy.test2(null);
		System.out.println(proxy.toString());
	}
}
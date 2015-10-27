package cglib;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class CGLibTest {
	public static void main(String[] args) throws Exception {
//		testMethodInterceptor();
		testCallbackFilter();
	}

	public static void testMethodInterceptor() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
					return "Hello cglib!";
				} else {
					return proxy.invokeSuper(obj, args);
				}
			}
		});
		SampleClass proxy = (SampleClass) enhancer.create();
		System.out.println(proxy.test(null));
		System.out.println(proxy.toString());
//		System.out.println(proxy.hashCode()); // Does not throw an exception or result in an endless loop.
	}

	public static void testCallbackFilter() throws Exception {
		Enhancer enhancer = new Enhancer();
		CallbackHelper callbackHelper = new CallbackHelper(SampleClass.class, new Class[0]) {
			@Override
			protected Object getCallback(Method method) {
				if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
					return new FixedValue() {
						@Override
						public Object loadObject() throws Exception {
							return "Hello cglib!";
						}
					};
				} else {
					return NoOp.INSTANCE; // A singleton provided by NoOp.
				}
			}
		};
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallbackFilter(callbackHelper);
		enhancer.setCallbacks(callbackHelper.getCallbacks());
		SampleClass proxy = (SampleClass) enhancer.create();
		System.out.println(proxy.test(null));
		System.out.println(proxy.toString());
//		proxy.hashCode(); // Does not throw an exception or result in an endless loop.
	}
}
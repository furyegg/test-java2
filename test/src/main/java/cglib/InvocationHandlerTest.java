package cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class InvocationHandlerTest {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
//					System.out.println("invoke: " + proxy.i);
					return "Hello cglib!";
				} else {
					throw new RuntimeException("Do not know what to do.");
				}
			}
		});
		SampleClass proxy = (SampleClass) enhancer.create();
		System.out.println(proxy.test(null));
//		System.out.println(proxy.toString());
	}
}

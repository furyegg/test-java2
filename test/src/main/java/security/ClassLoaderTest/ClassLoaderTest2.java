package security.ClassLoaderTest;

import java.lang.reflect.Method;

import javax.swing.JOptionPane;

public class ClassLoaderTest2 {

	public static void main(String[] args) {
		try {
			ClassLoader loader = new CryptoClassLoader(3);
			Class<?> c = loader.loadClass("security.ClassLoaderTest.HelloWorld");
			Method m = c.getMethod("main", String[].class);
			m.invoke(null, (Object) new String[] {});
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

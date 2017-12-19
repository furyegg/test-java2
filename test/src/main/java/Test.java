import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class Test {
	static class A {}

	static class B extends A {}

	static A a;

    public static void main(String[] args) {
		BigDecimal b = new BigDecimal(11.2300);
		System.out.println(b.setScale(0, RoundingMode.CEILING));
		URL url = Test.class.getClassLoader().getResource("org/apache");
	}

    private static List<A> get() {
		List<A> l = Lists.newArrayList();
		l.add(new B());
		return l;
    }
}
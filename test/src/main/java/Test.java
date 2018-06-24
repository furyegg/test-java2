import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {
	static class A {}

	static class B extends A {}

	static A a;

    public static void main(String[] args) {
		Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 2);
		map.forEach((k, v) -> System.out.println(k));
	}

    private static List<A> get() {
		List<A> l = Lists.newArrayList();
		l.add(new B());
		return l;
    }
}
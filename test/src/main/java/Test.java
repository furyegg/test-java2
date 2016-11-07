import com.google.common.collect.ImmutableSetMultimap;

import java.util.Map;

public class Test {
	public static void main(String[] args) {
		ImmutableSetMultimap.Builder<Object, Object> builder = ImmutableSetMultimap.builder();
		builder.put("ECR", "alias1");
		builder.put("ECR", "alias2");
		builder.put("FED", "alias3");
		ImmutableSetMultimap<Object, Object> map = builder.build();
		System.out.println(map);

		for (Map.Entry<Object, Object> entry : map.entries()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

	}

}
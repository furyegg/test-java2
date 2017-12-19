import java.math.BigDecimal;

public class UnsignedValue {
	public static void main(String[] args) {
		Integer i = 0B10000000000000000000000000000000;
		System.out.println(Integer.toBinaryString(i << 1));
		System.out.println(Integer.toBinaryString(i >>> 10));
		System.out.println(Integer.toHexString(Integer.MAX_VALUE));
		System.out.println(Integer.toUnsignedString(Integer.MAX_VALUE));

		Long l = 1L;
		Long l2 = l << 32;
		System.out.println(Long.toBinaryString(l2));
	}
}

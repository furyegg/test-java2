import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.converters.DateConverter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		DateConverter converter = new DateConverter();
		converter.setPattern("yyyy-MM-dd");
		Date convert = converter.convert(Date.class, "2010-02-11");
		System.out.println(convert);
		
		Object abc = ConvertUtils.convert("abc", Integer.class);
		System.out.println(abc);
	}

}
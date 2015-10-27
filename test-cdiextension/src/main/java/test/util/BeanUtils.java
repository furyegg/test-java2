package test.util;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.common.FormInstanceType;
import test.common.TopInf;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

public class BeanUtils {

	private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

	public static <T extends TopInf> T getBeanByFormInstanceType(Class<T> fieldType) {
		BeanManager bm = getBeanManager();
		String reg = ConfigPackageThreadLocal.getInstance().get();
		String formInstanceTypeValue = reg.equals("ECR") ? FormInstanceType.EXTERNAL_TOOLSET.toString() :
				FormInstanceType.INTERNAL_FIN.toString();

		Set<Bean<?>> beanSet = bm.getBeans(fieldType);
		for (Bean<?> bean : beanSet) {
			CreationalContext creationalContext = bm.createCreationalContext(bean);
			T ref = (T) bm.getReference(bean, fieldType, creationalContext);

			FormInstanceType formInstanceType = EnumUtils.getEnum(FormInstanceType.class, ref.getCalcEngine());
			if (formInstanceType != null && formInstanceTypeValue.equals(formInstanceType.toString())) {
				return ref;
			}
		}

		throw new RuntimeException("Failed to get bean instance for: " + fieldType.getName());
	}

	private static BeanManager getBeanManager() {
		try {
			InitialContext ic = new InitialContext();
			BeanManager bm = (BeanManager) ic.lookup("java:comp/BeanManager");
			return bm;
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
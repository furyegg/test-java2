package test.util;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.cdiextension.ProcessEngineLiteral;
import test.common.CalcEngineThreadLocal;
import test.constant.CalcEngine;
import test.common.TopIntf;
import test.web.IndexBean;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

public class CDIUtils {

	private static final Logger log = LoggerFactory.getLogger(IndexBean.class);

	public static <T> T getBeanByProcessEngineType(Instance<T> instance) {
		CalcEngine pet = CalcEngineThreadLocal.getInstance().get();
		Instance<T> selectedInst = instance.select(new ProcessEngineLiteral(pet));
		log.info("isUnsatisfied: {}", selectedInst.isUnsatisfied());
		if (selectedInst != null) {
			return selectedInst.get();
		}
		return null;
	}

	public static <T extends TopIntf> T getBean(final Class<T> beanClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beanClass);
		Dispatcher dispatcher = new Dispatcher() {
			@Override
			public Object loadObject() throws Exception {
				return getBean2(beanClass);
			}
		};
		enhancer.setCallback(dispatcher);

		return (T) enhancer.create();
	}

	public static <T extends TopIntf> T getBean2(Class<T> beanClass) {
		BeanManager bm = getBeanManager();
		CalcEngine pet = CalcEngineThreadLocal.getInstance().get();

		Set<Bean<?>> beanSet = bm.getBeans(beanClass);
		for (Bean<?> bean : beanSet) {
			CreationalContext<T> ctx = (CreationalContext<T>) bm.createCreationalContext(bean);
			T reference = (T) bm.getReference((Bean<?>) bean, beanClass, ctx);

			if (pet == null) {
				if (bean.getBeanClass().getAnnotation(Default.class) != null) {
					return reference;
				}
			} else {
				if (reference.getCalcEngineName().equals(pet.toString())) {
					return reference;
				}
			}
		}
		return null;
	}

	public static BeanManager getBeanManager() {
		BeanManager bm = null;
		try {
			InitialContext ic = new InitialContext();
			bm = (BeanManager) ic.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bm;
	}

}
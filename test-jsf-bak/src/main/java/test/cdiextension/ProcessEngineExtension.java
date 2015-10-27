package test.cdiextension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.common.TopIntf;
import test.util.CDIUtils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import java.lang.reflect.Field;
import java.util.Set;

public class ProcessEngineExtension<T> implements Extension {

	private static final Logger log = LoggerFactory.getLogger(ProcessEngineExtension.class);

	public void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		AnnotatedType<T> at = pat.getAnnotatedType();

		for (AnnotatedField field : at.getFields()) {
			if (field.isAnnotationPresent(InjectByCalcEngine.class)) {
				log.info("++++++++++++++++++++++++ InjectByCalcEngine present!!! {}", field);
			}
		}
	}

	public void processInjectionTarget(@Observes ProcessInjectionTarget<T> pit) {
		final InjectionTarget<T> it = pit.getInjectionTarget();
		final AnnotatedType<T> at = pit.getAnnotatedType();

//		for (Object ipObj : it.getInjectionPoints()) {
//			InjectionPoint ip = (InjectionPoint) ipObj;
//			log.info("{} => {}", ip.getBean().getBeanClass(), ip.getMember().getDeclaringClass());
//		}
//		log.info("-----------------------");

		InjectionTarget<T> wrapper = new InjectionTarget<T>() {
			@Override
			public T produce(CreationalContext<T> ctx) {
				return it.produce(ctx);
			}

			@Override
			public void dispose(T instance) {
				it.dispose(instance);
			}

			@Override
			public Set<InjectionPoint> getInjectionPoints() {
				return it.getInjectionPoints();
			}

			@Override
			public void inject(T instance, CreationalContext<T> ctx) {
				it.inject(instance, ctx);
				for (Field field : at.getJavaClass().getDeclaredFields()) {
					if (field.isAnnotationPresent(InjectByCalcEngine.class)) {
						Class<?> fieldType = field.getType();
						if (!TopIntf.class.isAssignableFrom(fieldType)) {
							throw new RuntimeException("Invalid type: " + fieldType);
						}

						TopIntf proxyBean = CDIUtils.getBean((Class<TopIntf>) fieldType);
						try {
							field.setAccessible(true);
							field.set(instance, proxyBean);
						} catch (Exception e) {
							throw new RuntimeException("Failed to inject: " + fieldType);
						}
					}
				}
			}

			@Override
			public void postConstruct(T instance) {
				it.postConstruct(instance);
			}

			@Override
			public void preDestroy(T instance) {
				it.preDestroy(instance);
			}
		};

		pit.setInjectionTarget(wrapper);
	}

}
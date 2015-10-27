package test.cdiextension;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.common.TopInf;
import test.interceptor.ConfigPackageSensitive;
import test.util.BeanUtils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import java.lang.reflect.Field;
import java.util.Set;

public class ConfigPackageExtension<T> implements Extension {

	private static final Logger log = LoggerFactory.getLogger(ConfigPackageExtension.class);

	public void processInjectionTarget(@Observes ProcessInjectionTarget<T> pit) {
		final AnnotatedType<T> at = pit.getAnnotatedType();
		final InjectionTarget<T> it = pit.getInjectionTarget();

		if (!at.getJavaClass().getName().startsWith("test.")) {
			return;
		}

		InjectionTarget<T> wrapper = new InjectionTarget<T>() {
			@Override
			public T produce(CreationalContext<T> creationalContext) {
				return it.produce(creationalContext);
			}

			@Override
			public void dispose(T t) {
				it.dispose(t);
			}

			@Override
			public Set<InjectionPoint> getInjectionPoints() {
				return it.getInjectionPoints();
			}

			@Override
			public void inject(T instance, CreationalContext<T> creationalContext) {
				it.inject(instance, creationalContext);

				for (Field field : at.getJavaClass().getDeclaredFields()) {
					Class<?> fieldType = field.getType();
					if (field.isAnnotationPresent(InjectByFormInstanceType.class)) {

						ClassFile cf = new ClassFile(false, at.getJavaClass().getName(), null);
						ConstPool cp = cf.getConstPool();
						AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);

						Annotation a = new Annotation("ConfigPackageSensitive", cp);
						// a.addMemberValue("name", new StringMemberValue("Chiba", cp));
						attr.setAnnotation(a);
						cf.addAttribute(attr);
						// cf.setVersionToJava5();

						processFormInstanceTypeSensitiveBean(instance, creationalContext, field, fieldType);

						if (at.getJavaClass().isAnnotationPresent(ConfigPackageSensitive.class)) {
							log.info("-------------- holy shit!!!! ---------------------------");
						}
					}
				}
			}

			private void processFormInstanceTypeSensitiveBean(T instance, CreationalContext<T> creationalContext,
					Field field, Class<?> fieldType) {
				TopInf bean = BeanUtils.getBeanByFormInstanceType((Class<TopInf>) fieldType);
				field.setAccessible(true);
				try {
					field.set(instance, bean);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}
			}

			@Override
			public void postConstruct(T t) {
				it.postConstruct(t);
			}

			@Override
			public void preDestroy(T t) {
				it.preDestroy(t);
			}
		};

		pit.setInjectionTarget(wrapper);
	}

}
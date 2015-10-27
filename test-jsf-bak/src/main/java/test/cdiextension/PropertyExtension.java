//package test.cdiextension;
//
//import javax.enterprise.context.spi.CreationalContext;
//import javax.enterprise.event.Observes;
//import javax.enterprise.inject.spi.AfterDeploymentValidation;
//import javax.enterprise.inject.spi.Annotated;
//import javax.enterprise.inject.spi.AnnotatedMethod;
//import javax.enterprise.inject.spi.AnnotatedParameter;
//import javax.enterprise.inject.spi.AnnotatedType;
//import javax.enterprise.inject.spi.Bean;
//import javax.enterprise.inject.spi.BeanManager;
//import javax.enterprise.inject.spi.Extension;
//import javax.enterprise.inject.spi.InjectionPoint;
//import javax.enterprise.inject.spi.InjectionTarget;
//import javax.enterprise.inject.spi.ProcessAnnotatedType;
//import javax.enterprise.inject.spi.ProcessInjectionTarget;
//import javax.faces.context.FacesContext;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Member;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class PropertyExtension<R> implements Extension {
//
//	private PropertyResolverBean propertyResolverBean;
//
//	void processAnnotatedType(@Observes ProcessAnnotatedType<R> pat, BeanManager beanManager) {
//
//		AnnotatedType<R> at = pat.getAnnotatedType();
//
//		// Check if there is any method defined as Property Resolver
//		for (AnnotatedMethod<? super R> method : at.getMethods()) {
//			if (method.isAnnotationPresent(PropertyResolver.class)) {
//				// Found Property Resolver method so let's create our
//				// property resolver bean
//				propertyResolverBean = new PropertyResolverBean(method, beanManager);
//				// Break the loop. In this example we assume that the first
//				// resolver method to be found is the one that will be used
//				break;
//			}
//		}
//
//	}
//
//	void AfterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
//		// Create our Property Resolver bean instance.
//		// Additionally initialize any eventual injectable parameter
//		// that is passed into our Property Resolver method.
//		propertyResolverBean.initializePropertyResolverBean();
//	}
//
//	<X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {
//
//		final InjectionTarget<X> it = pit.getInjectionTarget();
//		final AnnotatedType<X> at = pit.getAnnotatedType();
//
//		// Here we wrap all available Injection Targets in a
//		// custom wrapper that will add custom behavior to
//		// inject() method
//		InjectionTarget<X> wrapper = new InjectionTarget<X>() {
//
//			@Override
//			public X produce(CreationalContext<X> ctx) {
//				return it.produce(ctx);
//			}
//
//			@Override
//			public void dispose(X instance) {
//				it.dispose(instance);
//			}
//
//			@Override
//			public Set<InjectionPoint> getInjectionPoints() {
//				return it.getInjectionPoints();
//			}
//
//			// The container calls inject() method when it's performing field
//			// injection and calling bean initializer methods.
//			// Our custom wrapper will also check for fields annotated with
//			// @Property and resolve them by invoking the Property Resolver
//			// method
//			@Override
//			public void inject(X instance, CreationalContext<X> ctx) {
//				it.inject(instance, ctx);
//				for (Field field : at.getJavaClass().getDeclaredFields()) {
//					Property annotation = field.getAnnotation(Property.class);
//					if (annotation != null) {
//						String key = annotation.value();
//						field.setAccessible(true);
//						try {
//							field.set(instance, propertyResolverBean.resolveProperty(key, ctx));
//						} catch (IllegalArgumentException
//								| IllegalAccessException
//								| InvocationTargetException e) {
//							throw new RuntimeException("Could not resolve property", e);
//						}
//					}
//				}
//			}
//
//			@Override
//			public void postConstruct(X instance) {
//				it.postConstruct(instance);
//			}
//
//			@Override
//			public void preDestroy(X instance) {
//				it.preDestroy(instance);
//			}
//
//		};
//
//		pit.setInjectionTarget(wrapper);
//	}
//
//	public class PropertyResolverBean {
//
//		private final AnnotatedMethod<? super R> propertyResolverMethod;
//		private final BeanManager beanManager;
//		private Object propertyResolverInstance;
//		private List<InjectionPoint> propertyResolverParameters;
//		private final boolean propertyLocalePresent;
//
//		private PropertyResolverBean(AnnotatedMethod<? super R> propertyResolverMethod, BeanManager beanManager) {
//			this.propertyResolverMethod = propertyResolverMethod;
//			this.beanManager = beanManager;
//			this.propertyLocalePresent = checkLocaleParameter();
//		}
//
//		private void initializePropertyResolverBean() {
//
//			// Get any existing eligible bean based on the type of the Property
//			// Resolver method containing class.
//			Set<Bean<?>> beans = beanManager.getBeans(propertyResolverMethod.getJavaMember().getDeclaringClass());
//			final Bean<?> propertyResolverBean = beanManager.resolve(beans);
//			CreationalContext<?> creationalContext = beanManager.createCreationalContext(propertyResolverBean);
//
//			// Create the Property Resolver bean instance
//			propertyResolverInstance = beanManager.getReference(propertyResolverBean,
//					propertyResolverMethod.getJavaMember().getDeclaringClass(), creationalContext);
//
//			propertyResolverParameters = new ArrayList<>();
//
//			// We assume that the first parameter is the property to be resolved
//			int startIndex = 1;
//			if (propertyLocalePresent) {
//				// If we have the additional locale property then the first
//				// couple of parameters are the locale and the property key
//				// (first is the locale; second is the property key)
//				startIndex = 2;
//			}
//
//			// Create injection points for any additional Property Resolver
//			// method parameters. They will be later injected by the container
//			if (propertyResolverMethod.getParameters().size() > startIndex) {
//				int currentIndex = 0;
//				for (final AnnotatedParameter<? super R> parameter : propertyResolverMethod.getParameters()) {
//
//					if (currentIndex++ < startIndex) {
//						continue;
//					}
//
//					propertyResolverParameters.add(new InjectionPoint() {
//
//						@Override
//						public Type getType() {
//							return parameter.getBaseType();
//						}
//
//						@Override
//						public Set<Annotation> getQualifiers() {
//							Set<Annotation> qualifiers = new HashSet<Annotation>();
//							for (Annotation annotation : parameter.getAnnotations()) {
//								if (beanManager.isQualifier(annotation.annotationType())) {
//									qualifiers.add(annotation);
//								}
//							}
//							return qualifiers;
//						}
//
//						@Override
//						public Bean<?> getBeanByProcessEngineType() {
//							return propertyResolverBean;
//						}
//
//						@Override
//						public Member getMember() {
//							return parameter.getDeclaringCallable().getJavaMember();
//						}
//
//						@Override
//						public Annotated getAnnotated() {
//							return parameter;
//						}
//
//						@Override
//						public boolean isDelegate() {
//							return false;
//						}
//
//						@Override
//						public boolean isTransient() {
//							return false;
//						}
//
//					});
//
//				}
//			}
//
//		}
//
//		public String resolveProperty(String key, CreationalContext<?> ctx)
//				throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//
//			List<Object> parameters = new ArrayList<>();
//
//			// If the Locale property is present, it must be the first
//			// parameter in the Property Resolver method
//			if (propertyLocalePresent) {
//				parameters.add(FacesContext.getCurrentInstance().getViewRoot().getLocale());
//			}
//
//			// The property key is the next parameter
//			parameters.add(key);
//
//			// Resolve any eventual additional parameter to be injected by the
//			// CDI container
//			for (InjectionPoint parameter : propertyResolverParameters) {
//				parameters.add(beanManager.getInjectableReference(parameter, ctx));
//			}
//
//			// Call the property resolver method
//			return (String) propertyResolverMethod.getJavaMember().invoke(propertyResolverInstance,
//					parameters.toArray());
//		}
//
//		private boolean checkLocaleParameter() {
//			for (Annotation[] annotations : propertyResolverMethod.getJavaMember().getParameterAnnotations()) {
//				for (Annotation annotation : annotations) {
//					if (annotation.annotationType().equals(PropertyLocale.class)) {
//						return true;
//					}
//				}
//			}
//			return false;
//		}
//
//	}
//
//}
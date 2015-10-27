package test.interceptor;

import java.io.Serializable;

import org.slf4j.Logger;

import test.util.ConfigPackageThreadLocal;
import test.web.ConfigPackageBean;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@ConfigPackageSensitive
public class ConfigPackageSensitiveInterceptor implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private ConfigPackageBean configPackageBean;

	@AroundInvoke
	public Object storeConfigPackage(InvocationContext ic) throws Exception {
		log.info(ic.getMethod().toString() + " => " + configPackageBean.getConfigPackage());
		
		ConfigPackageThreadLocal.getInstance().set(configPackageBean.getConfigPackage());

		return ic.proceed();
	}

}
package test.web;

import com.google.common.collect.Lists;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.cdiextension.InjectByCalcEngine;
import test.common.CalcEngineThreadLocal;
import test.constant.CalcEngine;
import test.ejb.service.TestService;
import test.util.CDIUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Named("indexBean")
@ViewAccessScoped
public class IndexBean implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(IndexBean.class);

	private String msg = "abc";

	private TestService testService2 = CDIUtils.getBean(TestService.class);

	@InjectByCalcEngine
	private TestService testService;

	private String regPrefix;
	private List<String> regList = Lists.newArrayList();

	@PostConstruct
	private void init() {
		log.info("IndexBean init");

		regList.add("ECR");
		regList.add("FED");
		if (!regList.isEmpty()) {
			setRegPrefix(regList.get(0));
		}
	}

	public void regPrefixChanged() {
		log.info("Select Regulator changed to [{}]", regPrefix);

		CalcEngine pet = CalcEngine.TOOLSET;
		if (regPrefix.equals("FED")) {
			pet = CalcEngine.OCELOT;
		}
		CalcEngineThreadLocal.getInstance().set(pet);
	}

	public void test() {
//		CDIUtils.getBeanByProcessEngineType(testService).test();
		// testService2.test();
		testService.test();
	}

	private void temp() {
		BeanManager bm;
		try {
			InitialContext ic = new InitialContext();
			bm = (BeanManager) ic.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			e.printStackTrace();
			return;
		}
		Set<Bean<?>> beans = bm.getBeans(Object.class);
		log.info("bean size: {}", beans.size());
		for (Bean<?> bean : beans) {
			log.info("bean: {}", bean.getBeanClass());
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRegPrefix() {
		return regPrefix;
	}

	public void setRegPrefix(String regPrefix) {
		this.regPrefix = regPrefix;
		// regPrefixChanged();
	}

	public List<String> getRegList() {
		return regList;
	}

	public void setRegList(List<String> regList) {
		this.regList = regList;
	}
}
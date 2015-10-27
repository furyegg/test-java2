package test.ejb.service;

import org.slf4j.Logger;
import test.constant.CalcEngine;
import test.ejb.dao.TestDao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OcelotTestServiceBean implements TestService {

	@Inject
	private Logger log;

//	@Inject
	private TestDao testDao;

	@PostConstruct
	private void init() {
		log.info("ocelot test service init");
	}

	@Override
	public void test() {
		log.info("ocelot test service");
//		testDao.test();
	}

	@Override
	public String getCalcEngineName() {
		return CalcEngine.OCELOT.toString();
	}
}
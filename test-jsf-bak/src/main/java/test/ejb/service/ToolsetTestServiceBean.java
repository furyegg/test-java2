package test.ejb.service;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import test.constant.CalcEngine;
import test.ejb.dao.TestDao;

@Stateless
@Default
public class ToolsetTestServiceBean implements TestService {

	@Inject
	private Logger log;

//	@Inject
	private TestDao testDao;

	@PostConstruct
	private void init() {
		log.info("toolset test service init");
	}

	@Override
	public void test() {
		log.info("toolset test service");
//		testDao.test();
	}

	@Override
	public String getCalcEngineName() {
		return CalcEngine.TOOLSET.toString();
	}

}
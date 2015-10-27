package test.ejb.dao;

import org.slf4j.Logger;
import test.constant.CalcEngine;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Stateless
@Default
public class ToolsetTestDaoBean implements TestDao {

	@Inject
	private Logger log;

	@PostConstruct
	private void init() {
		log.info("toolset test dao init");
	}

	@Override
	public void test() {
		log.info("toolset test dao");
	}

	@Override
	public String getCalcEngineName() {
		return CalcEngine.TOOLSET.toString();
	}

}
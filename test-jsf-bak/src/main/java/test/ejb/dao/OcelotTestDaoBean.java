package test.ejb.dao;

import org.slf4j.Logger;
import test.constant.CalcEngine;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OcelotTestDaoBean implements TestDao {

	@Inject
	private Logger log;

	@PostConstruct
	private void init() {
		log.info("ocelot test dao init");
	}

	@Override
	public void test() {
		log.info("ocelot test dao");
	}

	@Override
	public String getCalcEngineName() {
		return CalcEngine.OCELOT.toString();
	}

}
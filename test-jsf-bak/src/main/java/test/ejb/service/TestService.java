package test.ejb.service;

import test.common.TopIntf;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface TestService extends TopIntf, Serializable {
	void test();
}
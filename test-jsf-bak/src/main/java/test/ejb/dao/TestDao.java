package test.ejb.dao;

import test.common.TopIntf;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface TestDao extends TopIntf, Serializable {
	void test();
}
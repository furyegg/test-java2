package test.ejb;

import test.common.TopInf;

public interface Calculator extends TopInf {

	int add(int x, int y);

	int subtract(int x, int y);

}
package test.ejb;

import test.interceptor.ConfigPackageSensitive;

import javax.ejb.Stateless;

@Stateless
@ConfigPackageSensitive
public class CalculatorBean implements CalculatorRemote, CalculatorLocal {
	public int add(int x, int y) {
		return x + y;
	}

	public int subtract(int x, int y) {
		return x - y;
	}
}

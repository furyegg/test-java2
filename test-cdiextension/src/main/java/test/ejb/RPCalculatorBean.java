package test.ejb;

import test.common.FormInstanceType;

import javax.ejb.Stateless;

@Stateless
public class RPCalculatorBean implements CalculatorRemote, CalculatorLocal {
	public int add(int x, int y) {
		return x + y;
	}

	public int subtract(int x, int y) {
		return x - y;
	}

	@Override
	public String getCalcEngine() {
		return FormInstanceType.INTERNAL_FIN.toString();
	}
}
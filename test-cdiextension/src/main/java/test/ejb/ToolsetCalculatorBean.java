package test.ejb;

import test.common.FormInstanceType;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless
@Default
public class ToolsetCalculatorBean implements CalculatorRemote, CalculatorLocal {
	public int add(int x, int y) {
		return x + y;
	}

	public int subtract(int x, int y) {
		return x - y;
	}

	@Override
	public String getCalcEngine() {
		return FormInstanceType.EXTERNAL_TOOLSET.toString();
	}
}
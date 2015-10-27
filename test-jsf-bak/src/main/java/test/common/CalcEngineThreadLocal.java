package test.common;

import test.constant.CalcEngine;

public class CalcEngineThreadLocal {

	private ThreadLocal<CalcEngine> processEngineTL = new ThreadLocal<>();

	private static CalcEngineThreadLocal instance = new CalcEngineThreadLocal();

	private CalcEngineThreadLocal() {
	}

	public static CalcEngineThreadLocal getInstance() {
		return instance;
	}

	public CalcEngine get() {
		return processEngineTL.get();
	}

	public void set(CalcEngine pet) {
		processEngineTL.set(pet);
	}

}
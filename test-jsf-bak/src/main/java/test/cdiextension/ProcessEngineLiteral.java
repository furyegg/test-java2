package test.cdiextension;

import test.constant.CalcEngine;

import javax.enterprise.util.AnnotationLiteral;

public class ProcessEngineLiteral extends AnnotationLiteral<ProcessEngine> implements ProcessEngine {

	private CalcEngine pet;

	public ProcessEngineLiteral(CalcEngine pet) {
		this.pet = pet;
	}

	@Override
	public CalcEngine value() {
		return pet;
	}

}
package org.jboss.tutorial.stateless.client;

import javax.naming.InitialContext;

import test.ejb.Calculator;

public class Client {
	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();
		Calculator calculator = (Calculator) ctx.lookup("CalculatorBean/remote");

		System.out.println("1 + 1 = " + calculator.add(1, 1));
		System.out.println("1 - 1 = " + calculator.subtract(1, 1));
	}
}

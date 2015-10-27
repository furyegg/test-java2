package test.selenium;

import com.thoughtworks.selenium.SeleneseTestCase;

@SuppressWarnings("deprecation")
public class RCTest extends SeleneseTestCase {

	@Override
	public void setUp() throws Exception {
		setUp("http://www.google.com/", "*firefox");
	}

	public void testNew() throws Exception {
		selenium.open("/");
		selenium.type("q", "selenium rc");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Results * for selenium rc"));
	}

}

package test.login;

import java.net.URL;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class LoginTest extends AbstractArquillianTest {

	@Drone
	private DefaultSelenium browser;

	@ArquillianResource
	private URL deploymentUrl;

	@Test
	@RunAsClient
	public void should_login_successfully() {
		browser.open(processURL(deploymentUrl) + "login.jsf");

		browser.type("id=loginForm:username", "demo");
		browser.type("id=loginForm:password", "demo");
		browser.click("id=loginForm:login");
		browser.waitForPageToLoad("15000");

		Assert.assertTrue("User should be logged in!",
				browser.isElementPresent("xpath=//li[contains(text(), 'Welcome')]"));
		Assert.assertTrue("Username should be shown!",
				browser.isElementPresent("xpath=//p[contains(text(), 'You are signed in as demo.')]"));
	}

}

package test.login;

import java.net.URL;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Arquillian.class)
public class LoginTest2 extends AbstractArquillianTest {

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@Test
	@RunAsClient
	public void should_login_successfully() {
		browser.get(processURL(deploymentUrl) + "login.jsf");

		browser.findElement(By.id("loginForm:username")).sendKeys("demo");
		browser.findElement(By.id("loginForm:password")).sendKeys("demo");
		browser.findElement(By.id("loginForm:login")).click();

		(new WebDriverWait(browser, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return browser.findElement(By.xpath("//li[contains(text(), 'Welcome')]")).isDisplayed();
			}
		});

		Assert.assertTrue("User should be logged in!",
				browser.findElement(By.xpath("//li[contains(text(), 'Welcome')]")).isDisplayed());
		Assert.assertTrue("Username should be shown!",
				browser.findElement(By.xpath("//li[contains(text(), 'Welcome')]")).isDisplayed());
	}

}

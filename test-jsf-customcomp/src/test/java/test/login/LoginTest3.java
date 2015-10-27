package test.login;

import java.net.URL;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

@RunWith(Arquillian.class)
public class LoginTest3 extends AbstractArquillianTest {

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@FindBy(id = "loginForm:userName")
	private WebElement username;

	@FindBy(id = "loginForm:password")
	private WebElement password;

	@FindBy(id = "loginForm:login")
	private WebElement loginButton;

	@FindBy(tagName = "li")
	private WebElement facesMessage; // 2. injects a first element with given tag name

	@FindByJQuery("p:visible")
	private WebElement signedAs; // 3. injects an element using jQuery selector

	@FindBy(css = "input[type=submit]")
	private WebElement whoAmI;

	@Test
	@RunAsClient
	public void should_login_successfully() throws Exception {
		browser.get(processURL(deploymentUrl) + "login2.jsf"); // 1. open the tested page

		username.sendKeys("demo"); // 3. control the page
		password.sendKeys("demo");

		captureScreen("login");

		Graphene.guardHttp(loginButton).click();

		// Graphene.waitModel().until().element(facesMessage).is().present();
		Assert.assertEquals("Welcome", facesMessage.getText().trim());

		WebElement img = browser.findElement(By.tagName("img"));
		// new Actions(browser).moveToElement(img).click().build().perform();
//		JavascriptExecutor executor = (JavascriptExecutor) browser;
//		executor.executeScript("arguments[0].click();", img);

		Graphene.guardAjax(whoAmI).click();
		Graphene.waitAjax().until().element(signedAs).is().present();
		captureScreen("home2");

		Assert.assertTrue(signedAs.getText().contains("demo"));
	}

}

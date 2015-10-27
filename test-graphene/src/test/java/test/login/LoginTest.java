package test.login;

import java.net.URL;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class LoginTest extends AbstractArquillianTest {

	private static Logger log = LoggerFactory.getLogger(LoginTest.class);

	static final String WEBAPP_SRC = "src/main/webapp";

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@FindBy
	private WebElement userName;

	@FindBy
	private WebElement password;

	@FindBy(id = "login")
	private WebElement loginButton;

	@FindBy(tagName = "li")
	private WebElement facesMessage;

	@FindByJQuery("p:visible")
	private WebElement signedAs;

	@FindBy(css = "input[type=submit]")
	private WebElement whoAmI;

	@Test
	@InSequence(1)
	public void should_login_successfully() {
		String url = processURL(deploymentUrl);
		log.info(url);
		browser.get(url + "login.jsf"); // 1. open the tested page

		userName.sendKeys("demo"); // 3. control the page
		password.sendKeys("demo");

		Graphene.guardHttp(loginButton).click();

		Assert.assertEquals("Welcome", facesMessage.getText().trim());

		WebElement img = browser.findElement(By.tagName("img"));
		new Actions(browser).moveToElement(img).click().build().perform();

		// whoAmI.click();
		// Graphene.waitAjax().until().element(signedAs).is().present();
		// Assert.assertTrue(signedAs.getText().contains("demo"));
	}

}

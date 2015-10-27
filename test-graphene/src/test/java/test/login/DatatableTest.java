package test.login;

import java.net.URL;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class DatatableTest extends AbstractArquillianTest {

	private static Logger log = LoggerFactory.getLogger(DatatableTest.class);

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@ArquillianResource
	private Actions actions;

	@Test
	@InSequence(1)
	public void selectRowTest() {
		browser.get(processURL(deploymentUrl) + "datatable.jsf");

		WebElement carTable = browser.findElement(By.id("form:cars"));
		Assert.assertTrue(carTable.isDisplayed());

		WebElement carBand = carTable.findElement(ByJQuery.selector("td:contains('Audi')"));
		Point p = carBand.getLocation();
		// actions.moveByOffset(p.getX(), p.getY()).click().build().perform();
		actions.moveToElement(carBand).click().build().perform();

		WebElement viewBtn = browser.findElement(By.id("form:cars:viewButton"));
//		Graphene.guardAjax(viewBtn).click();

		// Graphene.
		final WebElement dialogTitle = browser.findElement(ByJQuery.selector("div[id='form:dialog']:has(span:contains('Car Detail'))"));
		final WebElement dialogText = browser.findElement(ByJQuery.selector("table[id='form:display']:has(td:contains('Audi'))"));
		// sleep(1000);
		
		new WebDriverWait(browser, 5).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				// return browser.findElement(ByJQuery.selector("table[id='form:display']:has(td:contains('Audi'))")).isDisplayed();
				return dialogText.isDisplayed();
			}
		});

		// Graphene.waitAjax().until().element(dialogTitle).is().visible();
		
		Assert.assertTrue(dialogText.isDisplayed());
	}
}

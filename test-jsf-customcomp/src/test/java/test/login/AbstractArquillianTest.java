package test.login;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public abstract class AbstractArquillianTest {

	static final String WEBAPP_SRC = "src/main/webapp";
	static final String SETTINGS_PATH = "d:/maven/settings.xml";

	protected TakesScreenshot takesScreenshot;

	@Before
	public void init() {
		takesScreenshot = (TakesScreenshot) GrapheneContext.getContextFor(Default.class).getWebDriver(
				TakesScreenshot.class);
	}

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		PomEquippedResolveStage pom = Maven.configureResolver().fromFile(SETTINGS_PATH).loadPomFromFile("pom.xml");

		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "login-test.war")
				.addAsLibraries(pom.importRuntimeDependencies().resolve().withTransitivity().asFile())
				.addPackages(true, "test.login")
				// .addAsWebResource(WEBAPP_SRC + "/icons/33.png")
				.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class), "/", Filters.include(".*\\.xhtml$"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");

		war.as(ZipExporter.class).exportTo(new File("d:/ocelot/login-test.war"), true);

		return war;
	}
	
	protected void captureScreen(String fileName) throws Exception {
		File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File targetFile = new File("D:/selenium", fileName + ".png");
		FileUtils.copyFile(screenshot, targetFile);
	}
	
	protected String processURL(URL deploymentUrl) {
		String url = deploymentUrl.toExternalForm();
		return url.replace("0.0.0.0", "127.0.0.1");
	}

}
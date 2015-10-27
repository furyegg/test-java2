package test.login;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.openqa.selenium.TakesScreenshot;

public class AbstractArquillianTest {

	static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
		File[] libs = pom.importRuntimeDependencies().resolve().withTransitivity().asFile();

		WebArchive war = ShrinkWrap
				.create(WebArchive.class, "login-test.war")
				.addPackages(true, "test.login")
				.addAsLibraries(libs)
				.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(WEBAPP_SRC).as(GenericArchive.class), "/",
						Filters.includeAll()).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");

		// war.as(ZipExporter.class).exportTo(new File("d:/arquillian/login-test.war"), true);
		return war;
	}

	@ArquillianResource
	private TakesScreenshot screenshotter;

//	@Rule
//	public OcelotTestWatcher testWatcher = new OcelotTestWatcher();

	protected void sleep(long sleepInMillis) {
		try {
			Thread.sleep(sleepInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected String processURL(URL url) {
		return url.toExternalForm().replace("0.0.0.0", "127.0.0.1");
	}

}
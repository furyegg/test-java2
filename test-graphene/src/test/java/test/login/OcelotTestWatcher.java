package test.login;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OcelotTestWatcher extends TestWatcher {

	private static Logger log = LoggerFactory.getLogger(OcelotTestWatcher.class);

	@Override
	protected void failed(Throwable e, Description description) {
		String outPath = "d:/arquillian/";
		
		TakesScreenshot screenshotter = (TakesScreenshot) GrapheneContext.getContextFor(Default.class).getWebDriver(TakesScreenshot.class);
		File srcFile = screenshotter.getScreenshotAs(OutputType.FILE);
		File destFile = new File(outPath + description.getMethodName() + ".png");
		try {
			FileUtils.copyFile(srcFile, destFile);

			PrintWriter out = new PrintWriter(outPath + description.getMethodName() + ".txt");
			e.printStackTrace(out);
			out.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

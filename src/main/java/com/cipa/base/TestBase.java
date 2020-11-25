package com.cipa.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.cipa.driver.BrowserDriverFactory;
import com.cipa.driver.SelDriver;
import com.cipa.utils.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.LocalFileDetector;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Listeners({TestListener.class})
public class TestBase {

	private static final Logger LOG = LogManager.getLogger(TestBase.class);
	protected SelDriver driver;
	protected static ExtentReports reports;
	protected static ExtentTest test;

	@BeforeSuite(alwaysRun = true)
	protected void startReporter(){
		LOG.info("Execute Before Suite");
		ExtentSparkReporter html = new ExtentSparkReporter(PropertyReader.getProperty("report.folder")+"extentreport.html");
		html.config().setDocumentTitle("Automation Report");
		reports = new ExtentReports();
		reports.attachReporter(html);
	}

	@BeforeMethod(alwaysRun = true)
	protected void setUp(Method method) {
		//Can inject only one of <ITestContext, XmlTest, Method, Object[], ITestResult> into a @BeforeMethod annotated setUp.
		LOG.info("Execute Before Method");
		String testName = method.getName();
		BrowserDriverFactory factory = new BrowserDriverFactory(PropertyReader.getProperty("browser"));

		if ("sauce".equals(PropertyReader.getProperty("environment"))) {
			driver = factory.createDriverInSauceLabs(PropertyReader.getProperty("sauce.platform"), testName);
		} else {
			driver = factory.createLocalDriver();
		}

		// maximize browser window
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// use local files when uploading files in node browsers
		driver.setFileDetector(new LocalFileDetector());
		test = reports.createTest(this.getClass().getSimpleName()).info("Starting Test");
	}

	@AfterMethod(alwaysRun = true)
	protected void tearDown(ITestResult result) {
		LOG.info("Execute Teardown");
		switch (result.getStatus()){
			case ITestResult.FAILURE:
				test.fail("Test FAILED! Reason:" + result.getThrowable());

				// Take screenshot
				try{
					String fileName = result.getName() + new Date().getTime()+".png";
					driver.takeScreenshot(fileName);
					test.fail("Screenshot: ", MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
				} catch (IOException e) {
					LOG.debug(e);
				}
				break;
			case ITestResult.SUCCESS:
				test.pass("Test PASSED!");
				break;
			case ITestResult.SKIP:
				test.skip("Test SKIPPED!");
				break;
			default:
				test.fail("Test STARTED but not finished.");
				break;
		}

		// Closing driver
		LOG.info("Closing driver");
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterSuite(alwaysRun = true)
	protected void endReport(){
		reports.flush();
	}

}

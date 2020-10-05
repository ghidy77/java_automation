package com.cipa.base;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.cipa.driver.BrowserDriverFactory;
import com.cipa.driver.SelDriver;

public class BaseTest {

	protected SelDriver driver;

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser", "environment", "platform" })
	protected void setUp(@Optional("chrome") String browser, @Optional("local") String environment,
			@Optional String platform, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName();
		BrowserDriverFactory factory = new BrowserDriverFactory(browser);

		// Starting tests locally or on the grid depending on the environment parameter
		if (environment.equals("sauce")) {
			driver = factory.createDriverInSauceLabs(platform, testName);
		} else {
			driver = factory.createDriver();
		}

		// maximize browser window
		driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	protected void tearDown() {
		// Closing driver
		System.out.println("Closing driver");
		if (driver != null) {
			driver.quit();
		}
	}

}

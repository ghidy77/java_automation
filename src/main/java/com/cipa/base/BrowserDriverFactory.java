package com.cipa.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserDriverFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private String browser;

	public BrowserDriverFactory(String browser) {
		this.browser = browser.toLowerCase();
	}

	public WebDriver createDriver() {

		// Creating driver
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver.set(new ChromeDriver());
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver.set(new FirefoxDriver());
			break;
			
		default:
			System.out.println("Error setting parameter: " + browser);
			break;
		}

		return driver.get();
	}

	/** Starting tests using Selenium grid */
	public WebDriver createDriverGrid() {
		// Setting up selenium grid hub url
		URL url = null;
		try {
			url = new URL("http://192.168.0.2:4444/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Starting " + browser + " on grid");

		// Using DesiredCapabilities to set up browser
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browser);

		// Creating driver
		try {
			driver.set(new RemoteWebDriver(url, capabilities));
		} catch (WebDriverException e) {
			System.out.println("Error setting the browser: "+browser);
		}

		return driver.get();
	}

	/** Starting tests using sauce labs grid */
	public WebDriver createDriverSauce(String platform, String testName) {
		System.out.println("Starting Saucelabs");
		String username = "USER";
		String accessKey = "PASSWORD";
		String url = "http://" + username + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub";

		// Setting up DesiredCapabilities (browser and os)
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", browser);

		if (platform == null) {
			caps.setCapability("platform", "Windows 10");
		} else {
			caps.setCapability("platform", platform);
		}

		// Setting up test name
		caps.setCapability("name", testName);

		try {
			driver.set(new RemoteWebDriver(new URL(url), caps));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver.get();
	}

}

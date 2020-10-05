package com.cipa.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserDriverFactory {

	private String browser;

	public BrowserDriverFactory(String browser) {
		this.browser = browser.toLowerCase();
	}

	public SelDriver createDriver() {

		URL url;
		try {
			url = new URL("http://192.168.0.1:4444/wd/hub");
		} catch (MalformedURLException e) {
			throw new IllegalStateException("Invalid URL of remote web driver");
		}
		// Creating driver
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			return new SelDriver(url, new ChromeOptions());

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			return new SelDriver(url, new FirefoxOptions());
		default:
			throw new IllegalStateException("Invalid Browser parameter");
		}

	}

	/** Starting tests using sauce labs grid */
	public SelDriver createDriverInSauceLabs(String platform, String testName) {
		System.out.println("Starting Saucelabs");
		String username = "USER";
		String accessKey = "PASSWORD";
		String urlSauce = "http://" + username + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub";
		URL url;
		try {
			url = new URL(urlSauce);
		} catch (MalformedURLException e) {
			throw new IllegalStateException("Malformed URL exception encountered when accessing SauceLabs Grid: ");
		}
		// Setting up DesiredCapabilities (browser and os)
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", browser);
		caps.setCapability("name", testName);

		if (platform.isEmpty()) {
			caps.setCapability("platform", "Windows 10");
		} else {
			caps.setCapability("platform", platform);
		}

		return new SelDriver(url, caps);
	}

}

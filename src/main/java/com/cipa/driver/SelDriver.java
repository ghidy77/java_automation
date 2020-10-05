package com.cipa.driver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelDriver extends RemoteWebDriver {

	public SelDriver(URL remoteAddress, Capabilities capabilities) {
		super(remoteAddress, capabilities);
	}

	@Override
	public void get(String url) {
		System.out.println("Open page: " + url);
		super.get(url);
		waitForPageToLoad();
	}

	public void waitForPageToLoad() {

		JavascriptExecutor js = (JavascriptExecutor) this;
		boolean isJQueryComplete;
		boolean domReady = "complete".equals(js.executeScript("return document.readyState").toString());
		boolean isJQuery = "true".equals(js.executeScript("return window.jQuery != undefined").toString());
		if (isJQuery) {
			isJQueryComplete = "0".equals(js.executeScript("return jQuery.active").toString());
		} else {
			isJQueryComplete = true;
		}

		waitFor(wd -> domReady && isJQueryComplete, null);
	}

	public <T> void waitFor(ExpectedCondition<T> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
		WebDriverWait wait = new WebDriverWait(this, timeOutInSeconds);
		wait.until(condition);
	}

	protected void takeScreenshot(String fileName) throws IOException {
		File scrFile = getScreenshotAs(OutputType.FILE);
		File path = new File(System.getProperty("user.dir") + "//test-output//screenshots//" + fileName + ".png");
		Files.copy(scrFile.toPath(), path.toPath());
	}

	public Actions dragAndDrop(By from, By to) {
		return new Actions(this).dragAndDrop(findElement(from), findElement(to));
	}

	public void switchFocusFromMainWindow() {
		String mainWindow = getWindowHandle();
		for (String window : getWindowHandles()) {
			if (!window.equals(mainWindow)) {
				switchTo().window(window);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public WebElement findElementInUserVisibleViewport(By locator) {
		WebElement element = findElement(locator);
		Map<String, Object> result = (Map<String, Object>) executeScript(
				"var result = arguments[0].getBoundingClientRect(); return (result instanceof Object ? result : {})",
				element);
		if (result.isEmpty()) {
			return null;
		}
		Double top = Double.parseDouble(result.get("top").toString());
		Double left = Double.parseDouble(result.get("left").toString());
		Double bottom = Double.parseDouble(result.get("bottom").toString());
		Double right = Double.parseDouble(result.get("right").toString());
		Double windowInnerHeight = Double.parseDouble(executeScript("return window.innerHeight").toString());
		Double windowInnerWidth = Double.parseDouble(executeScript("return window.innerWidth").toString());

		System.out.println(element.toString() + " location: " + "TOP: " + top + "  LEFT:" + left + "  BOTTOM:" + bottom
				+ "  RIGHT:" + right + "  Window Inner Height:" + windowInnerHeight + "  Window Inner Width:"
				+ windowInnerWidth);
		// presence in viewport is validated here
		if (top >= 0 && left >= 0 && bottom <= windowInnerHeight && right <= windowInnerWidth) {
			return element;
		} else {
			return null;
		}
	}

}

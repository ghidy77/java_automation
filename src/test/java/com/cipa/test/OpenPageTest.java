package com.cipa.test;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenPageTest {
	@Test
	public void openPageTest() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		
		WebDriver driver = new ChromeDriver(); 
		driver.get("http://www.filelist.ro");
		WebElement page = driver.findElement(By.cssSelector("body"));

		System.out.println("open page");
		Assert.assertTrue(page.getText().contains("Login"), "Page should contain login");
		driver.quit();
	}
}

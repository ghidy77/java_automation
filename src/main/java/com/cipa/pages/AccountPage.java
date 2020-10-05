package com.cipa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cipa.base.BasePage;
import com.cipa.driver.SelDriver;

public class AccountPage extends BasePage {

	private By menu = By.cssSelector(".account-menu");
	private By accountText = By.name("account-details");


	public AccountPage(SelDriver driver) {
		super(driver);
		driver.waitFor(ExpectedConditions.visibilityOf(driver.findElement(menu)), DEFAULT_WAIT);
	}


	public String getUserDetails() {
		return driver.findElement(accountText).getText();
	}
}

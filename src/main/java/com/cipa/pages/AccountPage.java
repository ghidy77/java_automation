package com.cipa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cipa.base.PageBase;
import com.cipa.driver.SelDriver;

public class AccountPage extends PageBase {

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

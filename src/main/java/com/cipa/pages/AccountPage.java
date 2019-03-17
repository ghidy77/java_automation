package com.cipa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cipa.base.BasePageObject;

public class AccountPage extends BasePageObject {

	private By menu = By.cssSelector(".account-menu");
	private By accountText = By.name("account-details");


	public AccountPage(WebDriver driver) {
		super(driver);
	}


	/** Wait for message to be visible on the page */
	public void waitForPageToLoad() {
		waitForVisibilityOf(menu, 5);
	}


	public String getUserDetails() {
		return find(accountText).getText();
	}
}

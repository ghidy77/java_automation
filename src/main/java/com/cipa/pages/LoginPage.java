package com.cipa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cipa.base.BasePageObject;

public class LoginPage extends BasePageObject {

	private String baseURL = "http://example.com/login";

	private By userInput = By.cssSelector(".username");
	private By passwordInput = By.cssSelector(".password");
	private By loginButton = By.id("submit");


	public LoginPage(WebDriver driver) {
		super(driver);
	}


	/* Open page */
	public void goToPage() {
		get(baseURL);
	}


	/* Wait for username input to be visible on the page */
	public void waitForLoginPageToLoad() {
		waitForVisibilityOf(userInput, 5);
	}


	/* Valid log in */
	public AccountPage login(String username, String password) {
		find(userInput).sendKeys(username);
		find(passwordInput).sendKeys(password);
		find(loginButton).click();
		
		return new AccountPage(driver);
	}
}

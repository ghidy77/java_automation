package com.cipa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cipa.base.BasePageObject;

public class HomePage extends BasePageObject {

	private String baseURL = "http://example.com/";

	private By signInButton = By.partialLinkText("Sign In");

	public HomePage(WebDriver driver) {
		super(driver);
	}

	/* Open page */
	public void goToPage() {
		get(baseURL);
	}


	/** Open LoginPage by clicking on Form Authentication Link */
	public LoginPage clickLoginButton() {
		find(signInButton).click();
		return new LoginPage(driver);
	}

}

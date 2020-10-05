package com.cipa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cipa.base.BasePage;
import com.cipa.driver.SelDriver;

public class LoginPage extends BasePage {

	@FindBy(css = ".username")
	WebElement userInput;
	@FindBy(css = ".password")
	WebElement passwordInput;
	@FindBy(id = "submit")
	WebElement loginButton;

	public LoginPage(SelDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		driver.waitFor(wd -> wd.getCurrentUrl().toLowerCase().contains("login"), DEFAULT_WAIT);
		driver.waitFor(ExpectedConditions.visibilityOf(userInput), DEFAULT_WAIT);
	}

	/* Valid log in */
	public AccountPage login(String username, String password) {
		userInput.sendKeys(username);
		passwordInput.sendKeys(password);
		loginButton.click();
		driver.waitForPageToLoad();

		return new AccountPage(driver);
	}
}

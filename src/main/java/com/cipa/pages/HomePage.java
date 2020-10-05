package com.cipa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cipa.base.BasePage;
import com.cipa.driver.SelDriver;

public class HomePage extends BasePage {

	@FindBy(partialLinkText = "Sign In" )
	WebElement signInButton;

	public HomePage(SelDriver driver) {
		super(driver);
		driver.waitFor(wd-> wd.getCurrentUrl().toLowerCase().contains("homepage"), DEFAULT_WAIT);
		PageFactory.initElements(driver, this);
	}

	/** Open LoginPage by clicking on Form Authentication Link */
	public LoginPage clickLoginButton() {
		signInButton.click();
		return new LoginPage(driver);
	}

}

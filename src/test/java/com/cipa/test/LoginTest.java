package com.cipa.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cipa.base.*;
import com.cipa.pages.*;


public class LoginTest extends BaseTest {

	@Test(priority = 1)
	public void logInTest() {
		
		String username = "test_user";
		
		// Open Home Page
		HomePage homePage = new HomePage(driver);
		homePage.goToPage();

		// Click Sign In Button
		LoginPage loginPage = homePage.clickLoginButton();

		// Wait for login page to load
		loginPage.waitForLoginPageToLoad();

		// login
		AccountPage accountPage = loginPage.login(username, "test123#");

		// wait for account page
		accountPage.waitForPageToLoad();
		String userDetails = accountPage.getUserDetails();

		Assert.assertTrue(userDetails.contains("Welcome, user " + username), "Message doesn't contain expected text.");
	}

}

package com.cipa.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cipa.base.*;
import com.cipa.pages.*;


public class LoginTest extends TestBase {
	
	public static final String BASE_URL = "https://www.example.org";

	@Test(description = "Valid Login test")
	public void logInTest() {
		
		String username = "test_user";
		
		// Open Home Page
		driver.get(BASE_URL);
		
		HomePage homePage = new HomePage(driver);

		// Click Sign In Button
		LoginPage loginPage = homePage.clickLoginButton();

		// login
		AccountPage accountPage = loginPage.login(username, "test123#");

		// get user details from account page
		String userDetails = accountPage.getUserDetails();

		Assert.assertTrue(userDetails.contains("Welcome, user " + username), "Message doesn't contain expected text.");
	}

}

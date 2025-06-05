package testclasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.HomePage;
import pages.RegistrationPage;

public class Registration extends CommonToAllTest{
	
	@Test(groups="Regression")
	public void signUpPage() {
		logger.info("*****stratting test case execution*****");
		HomePage homepage = new HomePage(DriverManager.driver);
		homepage.clickonRegisterButton();
		logger.info("clicked on my account");
		RegistrationPage regPage = new RegistrationPage(DriverManager.driver);
		regPage.registrationPage();
		
		logger.info("validating expected message...");
		String cfmsg = regPage.getConfirmation();
		Assert.assertEquals(cfmsg, "Your Account Has Been Created!");
		
		
		
		
	}
	
	

}

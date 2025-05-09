package testclasses;

import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.HomePage;
import pages.RegistrationPage;

public class Registration extends CommonToAllTest{
	
	@Test
	public void loginPage() {
		HomePage homepage = new HomePage(DriverManager.driver);
		homepage.clickonRegisterButton();
		RegistrationPage regPage = new RegistrationPage(DriverManager.driver);
		regPage.registrationPage();
		
		
		
		
	}
	
	

}

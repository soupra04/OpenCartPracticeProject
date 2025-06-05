package testclasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.HomePage;
import pages.LoginPagePOM;
import pages.MyAccountPage;

public class LoginTest extends CommonToAllTest{
	
	@Test (groups = "sanity")
	public void login() {
		logger.info("**starting Test Case Execution for Login pageg***********");
	HomePage hp = new HomePage(DriverManager.driver);
	hp.clickonLoginButton();
	logger.info("**click on loginButton****");
	LoginPagePOM loginP = new LoginPagePOM(DriverManager.driver);
	 loginP.provideLoginCred();
	 
	 MyAccountPage accPage = new MyAccountPage(DriverManager.driver);
	 String msg=accPage.getmyAccText();
	
	Assert.assertEquals(msg, "My Account");
	
	
	}
   
}

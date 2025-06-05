package testclasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.HomePage;
import pages.LoginPagePOM;
import pages.MyAccountPage;
import utilities.DataProviders;

public class LoginDataDriverTest extends CommonToAllTest {
	
	@Test (dataProvider="LoginData", dataProviderClass = DataProviders.class)
	public void loginWithExcel(String email, String password,String res) {
		
		logger.info("**starting Test Case Execution for Login pageg***********");
		HomePage hp = new HomePage(DriverManager.driver);
		hp.clickonLoginButton();
		logger.info("**click on loginButton****");
		LoginPagePOM loginP = new LoginPagePOM(DriverManager.driver);
		 loginP.testloginWithExcel(email, password, res);
		 
		 MyAccountPage accPage = new MyAccountPage(DriverManager.driver);
		boolean targetPage = accPage.isMyAccountPageExists();
		if(res.equalsIgnoreCase("valid")) {
			if(targetPage==true) {
				accPage.logout();
				Assert.assertTrue(true);
				
			} else {
				Assert.assertTrue(false);
			}
		}
		if(res.equalsIgnoreCase("invalid")) 
		{
			if(targetPage==true) {
				accPage.logout();
				Assert.assertTrue(false);
				
			} else {
				Assert.assertTrue(true);
			}
		}
		
		
		
	}

}

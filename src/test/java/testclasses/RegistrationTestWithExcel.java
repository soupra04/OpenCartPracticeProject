package testclasses;

import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.HomePage;
import pages.RegistrationPage;
import utilities.DataProviders;

public class RegistrationTestWithExcel extends CommonToAllTest{
	
	@Test (dataProvider="LoginData3", dataProviderClass = DataProviders.class)
	public void registrationWithExcel(String fname,String lname,String eml,String tel,String pwd,String cpwd) {
		logger.info("*****stratting test case execution*****");
		
		HomePage hp = new HomePage(DriverManager.driver);
		hp.clickonRegisterButton();
		logger.info("clicked on Reg button");
		
		RegistrationPage rPpage = new RegistrationPage(DriverManager.driver);
		rPpage.registrationPage();
		
	}

}

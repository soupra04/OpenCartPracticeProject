package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.CommonToAllPages;
import utils.PropertiesReader;

public class LoginPagePOM extends CommonToAllPages{
	
	WebDriver driver;
	
	public LoginPagePOM(WebDriver driver) {
		super();
	}
	
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By submit = By.xpath("//input[@type='submit']");
	
	
	public void provideLoginCred() {
		
		enterInput(username, PropertiesReader.readkey("username"));
		enterInput(password, PropertiesReader.readkey("password"));
		clickElement(submit);
		  
	}
	
	
	public void testloginWithExcel(String user, String pwd, String res) {
		enterInput(username, user);
		enterInput(password,pwd);
		clickElement(submit);
		
	}

}

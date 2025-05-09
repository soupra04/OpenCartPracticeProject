package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.CommonToAllPages;

public class HomePage extends CommonToAllPages{
	
	public HomePage(WebDriver driver) {
		super();
		
	}
	
	private By registerlink = By.linkText("Register");
	private By loginLink = By.linkText("Login");
	private By myaccount = By.xpath("//li[@class='dropdown']/a[@title='My Account']");
	
	
	public void clickonRegisterButton() {
		
		
		
		driver.get("https://tutorialsninja.com/demo/");
		
		clickElement(myaccount);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickElement(registerlink);
		
		
	}
	
	public void clickonLoginButton() {
		driver.get("https://tutorialsninja.com/demo/");
		clickElement(myaccount);
		clickElement(loginLink);
	}

}

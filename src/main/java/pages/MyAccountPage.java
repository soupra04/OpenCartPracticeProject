package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.CommonToAllPages;

public class MyAccountPage  extends CommonToAllPages{
	
	public MyAccountPage(WebDriver driver) {
		super();
		
	}
	
	private By myaccText = By.xpath("//h2[normalize-space()='My Account']");
	private By logout = By.xpath("//li/a[normalize-space()='Logout']");
	private By myaccount = By.xpath("//li[@class='dropdown']/a[@title='My Account']");
	
	public  String getmyAccText() {
		return driver.findElement(myaccText).getText();
	}
	
	public void logout() {
		clickElement(myaccount);
		clickElement(logout);
		
	}

	public boolean isMyAccountPageExists() {
		// TODO Auto-generated method stub
		return true;
	}

}

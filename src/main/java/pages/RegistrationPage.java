package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.CommonToAllPages;

public class RegistrationPage extends CommonToAllPages {
	
	public RegistrationPage(WebDriver driver) {
		super();
	}
  private By fname= By.id("input-firstname");
  private By lname= By.id("input-lastname");
  private By ename= By.id("input-email");
  private By phone= By.id("input-telephone");
  private By pwd= By.id("input-password");
  private By cpwd = By.id("input-confirm");
  private By cbox = By.xpath("//input[@type='checkbox']");
  private By successMessage = By.xpath("//div[@id='content']/h1");
  
  public void registrationPage() {
	  
	  
	  enterInput(fname, randomeString().toUpperCase());
	  enterInput(lname, randomeString().toUpperCase());
	  //enterInput(ename, "soupra.cool+100@gmail.com");
	  enterInput(ename, randomeString() + "@gmail.com");
	  enterInput(phone, "9038479977");
	  String password = randomeAlphaNumeric();
	  enterInput(pwd, password);
	  enterInput(cpwd,  password);
	  clickElement(cbox);
	  
	 

	 WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
	 clickElement(submit);
	 
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	 wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
	 
	  
  }
  
  public String getConfirmation() {
	  try {
		  return driver.findElement(successMessage).getText();
	  } catch (Exception e) {
		  return e.getMessage();
	  }
	  
	  
  }
  
  
  
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
  private By cbox = By.id("//input[@type='checkbox']");
  
  
  public void registrationPage() {
	  
	  
	  enterInput(fname, "Soupra");
	  enterInput(lname, "Maity");
	  enterInput(ename, "soupra.cool@gmail.com");
	  enterInput(phone, "9038479977");
	  enterInput(pwd, "12345678");
	  enterInput(cpwd, "12345678");
	 // enterInput(cbox, "12345678");
  }
  
}

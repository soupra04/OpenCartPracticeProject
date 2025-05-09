package base;

import static driver.DriverManager.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonToAllPages {
	
	protected WebDriver driver;
	
	public CommonToAllPages() {
		this.driver = getDriver();
		
		
	}
	  public void clickElement(By by) {
		  getDriver().findElement(by).click();
	    }

	  
	  public void enterInput(By by, String key) {
		  getDriver().findElement(by).sendKeys(key);
	  }
	  
	  
}

package base;

import static driver.DriverManager.getDriver;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	  
	  public void clickElement(WebElement ele) {
		  ele.click();
	  }
	  
	  public String randomeString() {
		  String generateString = RandomStringUtils.randomAlphabetic(5);
		  return generateString;
	  }
	  
	  public String randomeAlphaNumeric() {
		  String generateString = RandomStringUtils.randomAlphabetic(5);
		  String generateNumber= RandomStringUtils.randomNumeric(8);
		  return (generateString+"@"+generateNumber);
	  }
	  
	  
	  
}

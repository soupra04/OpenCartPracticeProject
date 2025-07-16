package testclasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import utilities.DataProviders;

public class LoginInSalesforceWithExcelTest extends CommonToAllTest {
	 WebDriver driver;
	 Logger logger = LogManager.getLogger(ChangeMarginBasedonItemType.class);
	
	@Test (dataProvider="LoginData2", dataProviderClass = DataProviders.class)
	public void loginWithExcelData(String user, String pwd) {
		// TODO Auto-generated method stub
		
		logger.info("🚀 Starting Test Case Execution: Login and Edit Quote");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "--disable-notifications");
		driver = new ChromeDriver(options);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get(""); //url is  hidden due to privacy
		driver.findElement(By.id("username")).sendKeys(user);
		driver.findElement(By.id("password")).sendKeys(pwd);
		driver.findElement(By.name("Login")).submit();
		logger.info("✅ Login submitted");
		
		
		

	}
	@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed after test");
        }
    }

}

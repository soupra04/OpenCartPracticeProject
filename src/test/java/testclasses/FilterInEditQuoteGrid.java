package testclasses;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.CommonToAllTest;

public class FilterInEditQuoteGrid extends CommonToAllTest {
	static WebDriver driver;
	static Logger logger = LogManager.getLogger(ChangeMarginBasedonItemType.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		logger.info("🚀 Starting Test Case Execution: Login and Edit Quote");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "--disable-notifications");
		driver = new ChromeDriver(options);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get("https://computacenterplc--internalcc.sandbox.my.salesforce.com/");
		driver.findElement(By.id("username")).sendKeys("soupra.maity@computacenter.com.internalcc");
		driver.findElement(By.id("password")).sendKeys("Soupr@1234");
		driver.findElement(By.name("Login")).submit();
		logger.info("✅ Login submitted");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Actions action = new Actions(driver);

		WebElement quoteTab = driver.findElement(By.xpath("//a[@title='VCT Quotes']"));
		js.executeScript("arguments[0].click();", quoteTab);
		logger.info("✅ Navigated to 'VCT Quotes' tab");

		WebElement quote = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='QT-000101799']")));
		js.executeScript("arguments[0].scrollIntoView(true);", quote);
		js.executeScript("arguments[0].click();", quote);
		logger.info("✅ Opened Quote: QT-000101799");

		WebElement editQuoteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//li[@data-target-selection-name='sfdc:CustomButton.StrataVAR__CustomerBoM__c.StrataVAR__Edit_Quote']//button[normalize-space(text())='Edit Quote']")));
		js.executeScript("arguments[0].click();", editQuoteButton);
		logger.info("✅ Clicked on 'Edit Quote' button");

		WebElement iframeElement = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@title, 'Edit Quote')]")));
		driver.switchTo().frame(iframeElement);
		logger.info("✅ Switched to Edit Quote iframe");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("columnToolbar")));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='qtGrid']//tr[@tabindex='-1']"));
		logger.info("📊 Total rows found in grid: " + rows.size());
		
		for (int i=0;i< rows.size();i++) {
			
			WebElement row = rows.get(i);
			 List<WebElement> columns = row.findElements(By.tagName("td"));
			 
			 if(columns.size()<=20) {
				 logger.warn("⚠️ Row " + (i + 1) + " has fewer than expected columns. Skipping...");
				 continue;
			 } 
			 
			 WebElement itemType = columns.get(21);
			 WebElement itemTypeHeader = driver.findElement(By.xpath("//th[@id='qtGrid_StrataVAR__Item_Type__c']"));
			 action.doubleClick(itemTypeHeader).perform();
			 logger.info("✅ clicked on Item type header");
			//select[@name='StrataVAR__Item_Type__c']
			 WebElement itemTypeFilter = driver.findElement(By.xpath("//select[@name='StrataVAR__Item_Type__c']"));
			 Select select = new Select(itemTypeFilter);
			 select.selectByIndex(3);
			 logger.info("✅ clicked on Item type filter and Saas Filter is selected");
			 
			 
			
		}

	}

}

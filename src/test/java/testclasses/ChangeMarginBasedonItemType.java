package testclasses;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.CommonToAllTest;

//this are not done using POM , Just to demonstrate the workflow

public class ChangeMarginBasedonItemType extends CommonToAllTest {

	static WebDriver driver;
	static Logger logger = LogManager.getLogger(ChangeMarginBasedonItemType.class);

	public static void main(String[] args) throws InterruptedException {

		logger.info("🚀 Starting Test Case Execution: Login and Edit Quote");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "--disable-notifications");
		driver = new ChromeDriver(options);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get("");  //url is  hidden due to privacy
		driver.findElement(By.id("username")).sendKeys(""); //username is  hidden due to privacy
		driver.findElement(By.id("password")).sendKeys(""); // Password is  hidden due to privacy
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
		Thread.sleep(3000);

		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='qtGrid']//tr[@tabindex='-1']"));
		logger.info("📊 Total rows found in grid: " + rows.size());

		// ----------- PHASE 1: Log all Item Types -----------
		logger.info("🔍 Logging Item Types for each row...");
		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			List<WebElement> columns = row.findElements(By.tagName("td"));

			if (columns.size() <= 20) {
				logger.warn("⚠️ Row " + (i + 1) + " has fewer than expected columns. Skipping...");
				continue;
			}

			WebElement itemTypeCol = columns.get(21); // Adjust if required
			String itemType = itemTypeCol.getText().trim();
			logger.info("Row " + (i + 1) + " | Item Type: " + itemType);
		}

		// ----------- PHASE 2: Update Margin for SaaS Items -----------
		logger.info("✏️ Updating Margin % for 'SaaS' item types...");
		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			List<WebElement> columns = row.findElements(By.tagName("td"));

			if (columns.size() <= 20) {
				logger.warn("⚠️ Row " + (i + 1) + " has fewer than expected columns. Skipping...");
				continue;
			}

			WebElement itemTypeCol = columns.get(21); // Adjust if needed
			String itemType = itemTypeCol.getText().trim();

			if ("SaaS".equalsIgnoreCase(itemType)) {
				try {
					logger.info("🟡 Row " + (i + 1) + " is a SaaS item. Attempting margin update...");

					WebElement marginCell = columns.get(13);
					action.doubleClick(marginCell).perform();
					logger.info("🖱️ Double-clicked on margin cell in row " + (i + 1));

					WebElement marginInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
							"//input[@class='editable customelement' and @name='StrataVAR__VAR_Margin_Pct__c']")));
					marginInput.clear();
					marginInput.sendKeys("10");
					action.sendKeys(marginInput, Keys.ENTER).perform();
					logger.info("✅ Margin updated to 10% for row " + (i + 1));

					WebElement ccTotalCostFinalCell = row.findElement(
							By.xpath(".//td[@aria-describedby='qtGrid_StrataVAR__VAR_Total_Cost__c']"));
					WebElement ccMarginCellP = row.findElement(
							By.xpath(".//td[@aria-describedby='qtGrid_StrataVAR__VAR_Margin_Pct__c']"));
					WebElement custTotalPriceCell = row.findElement(
							By.xpath(".//td[@aria-describedby='qtGrid_StrataVAR__Cust_Extended_Price__c']"));

					String ccTotalCostFinalraw = ccTotalCostFinalCell.findElement(By.tagName("span"))
							.getAttribute("data-src");
					String ccMarginraw = ccMarginCellP.findElement(By.tagName("span")).getAttribute("data-src");
					String custTotalPriceraw = custTotalPriceCell.findElement(By.tagName("span"))
							.getAttribute("data-src");
					logger.info("Raw cell texts -Margin%: '" + ccMarginraw + "', CC total Cost Final: '" + ccTotalCostFinalraw
							+ "', Cust Total price: '" + custTotalPriceraw + "'");
					
					double ccMargin = ccMarginraw != null ? Double.parseDouble(ccMarginraw.trim().replaceAll("[$,]", "")) : 10;
					double ccTotalCostFinal = ccTotalCostFinalraw != null
							? Double.parseDouble(ccTotalCostFinalraw.trim().replaceAll("[$,]", ""))
							: 0.00;
					double custTotalPrice = custTotalPriceraw != null
							? Double.parseDouble(custTotalPriceraw.trim().replaceAll("[$,]", ""))
							: 0.00;

					double expectedCustTotalPrice = (ccTotalCostFinal / (1 - (ccMargin / 100))) ;
					
					logger.info("Row " + (i + 1) + " => Margin%: " + ccMargin + ", CC Total Cost final: " + ccTotalCostFinal + ", Expected Total cust price: "
							+ expectedCustTotalPrice + ", Actual Total cust price: " + custTotalPrice);
					double diff = expectedCustTotalPrice - custTotalPrice;

					if (Math.abs(diff) < 0.01) {
						logger.info("✅ Row " + (i + 1) + ": Calculated customer price is accurate.");
					} else {
						logger.error("❌ Row " + (i + 1) + ": Price mismatch. Difference = " + diff);
					}

				} catch (Exception e) {
					logger.error("❌ Failed to update margin or verify prices in row " + (i + 1), e);
				}
			}
		}

		// Save the grid
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("qtActBar-overlay")));
		WebElement saveBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//i[@id='btnsaveQuoteItems']]")));
		js.executeScript("arguments[0].scrollIntoView(true);", saveBtn);
		saveBtn.click();
		logger.info("✅ Clicked 'Save' button");

		Thread.sleep(1000);
		driver.quit();
		logger.info("🛑 Test execution completed and browser closed.");
	}
}

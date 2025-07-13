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

public class ChangingQtyInEditQuoteGrid extends CommonToAllTest {
	static WebDriver driver;
	static Logger logger = LogManager.getLogger(ChangingQtyInEditQuoteGrid.class);

	public static void main(String[] args) {

		logger.info("**starting Test Case Execution for Login page***********");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.get("https://computacenterplc--internalcc.sandbox.my.salesforce.com/");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.findElement(By.id("username")).sendKeys("soupra.maity@computacenter.com.internalcc");
		driver.findElement(By.id("password")).sendKeys("Soupr@1234");
		driver.findElement(By.name("Login")).submit();
		logger.info("clicked on Submit Button");

		Actions action = new Actions(driver);

		WebElement quoteTab = driver.findElement(By.xpath("//a[@title='VCT Quotes']"));
		js.executeScript("arguments[0].click();", quoteTab);
		logger.info("clicked on Quote Tab");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement quote = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='QT-000101755']")));
		js.executeScript("arguments[0].scrollIntoView(true);", quote);
		js.executeScript("arguments[0].click();", quote);

		WebElement editQuoteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//li[@data-target-selection-name='sfdc:CustomButton.StrataVAR__CustomerBoM__c.StrataVAR__Edit_Quote']//button[normalize-space(text())='Edit Quote']")));
		js.executeScript("arguments[0].click();", editQuoteButton);
		logger.info("clicked on Edit Quote grid button");

		WebElement iframeElement = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@title, 'Edit Quote')]")));
		driver.switchTo().frame(iframeElement);
		
		logger.info("Edit Quote grid is Opened");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='qtGrid']//tr[@tabindex=-1]"));

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);

			logger.info("Processing row " + (i + 1));

			try {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.size() < 7) {
					logger.warn("Row " + (i + 1) + " has fewer than expected columns.");
					continue;
				}

				WebElement qtyCellForClick = columns.get(5);
				action.doubleClick(qtyCellForClick).perform();
				logger.info("Qty field is double-clicked in row " + (i + 1));

				WebElement editableQty = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//input[@class='editable customelement' and @name='StrataVAR__Quantity__c']")));
				editableQty.clear();
				editableQty.sendKeys("300");

				// Click outside to trigger blur and update
				action.sendKeys(qtyCellForClick, Keys.ENTER).perform();
				// action.moveToElement(qtyCellForClick, 10, 10).click().perform();
				logger.info("Entered quantity 300 and clicked outside in row " + (i + 1));

				Thread.sleep(2000); // wait for UI update

				// Now read qty, list price, and total list price using aria-describedby in THIS
				// row only
				WebElement qtyCell = row
						.findElement(By.xpath(".//td[@aria-describedby='qtGrid_StrataVAR__Quantity__c']"));
				WebElement listPriceCell = row
						.findElement(By.xpath(".//td[@aria-describedby='qtGrid_StrataVAR__List_Price__c']"));
				WebElement totalListPriceCell = row
						.findElement(By.xpath(".//td[@aria-describedby='qtGrid_StrataVAR__Total_List_Price__c']"));
				String qtyTextRaw = qtyCell.getText();
				String listPriceTextRaw = listPriceCell.findElement(By.tagName("span")).getAttribute("data-src");
				String totalListPriceTextRaw = totalListPriceCell.findElement(By.tagName("span"))
						.getAttribute("data-src");
				logger.info("Raw cell texts - Qty: '" + qtyTextRaw + "', ListPrice: '" + listPriceTextRaw
						+ "', TotalListPrice: '" + totalListPriceTextRaw + "'");
				String qtyText = qtyTextRaw != null ? qtyTextRaw.trim().replaceAll("[^\\d.]", "") : "";
				String listPriceText = listPriceTextRaw != null ? listPriceTextRaw.trim().replaceAll("[$,]", "") : "";
				String totalListPriceText = totalListPriceTextRaw != null
						? totalListPriceTextRaw.trim().replaceAll("[$,]", "")
						: "";
				double qty = qtyText.isEmpty() ? 300 : Double.parseDouble(qtyText);
				double listPrice = listPriceText.isEmpty() ? 0.0 : Double.parseDouble(listPriceText);
				double totalLp = totalListPriceText.isEmpty() ? 0.0 : Double.parseDouble(totalListPriceText);
				double expectedTotal = listPrice * qty;
				logger.info("Row " + (i + 1) + " => Qty: " + qty + ", List Price: " + listPrice + ", Expected Total: "
						+ expectedTotal + ", Actual Total: " + totalLp);

				if (expectedTotal == totalLp) {
					logger.info("✅ Row " + (i + 1) + ": Total List Price is exactly correct.");
				} else {
					double diff = expectedTotal - totalLp;
					logger.error("❌ Row " + (i + 1) + ": Total List Price mismatch! Expected: " + expectedTotal
							+ ", Actual: " + totalLp + ", Difference: " + diff);
				}
			} catch (Exception e) {
				logger.warn("Could not find or edit input for row " + (i + 1), e);
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement saveBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//i[@id='btnsaveQuoteItems']]")));
		js.executeScript("arguments[0].scrollIntoView(true);", saveBtn);
		saveBtn.click();
		logger.info("Save button is clicked successfully");
	}
}
package testclasses;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import base.CommonToAllTest;

public class CheckSortinginGrid extends CommonToAllTest {
    static WebDriver driver;
    static Logger logger = LogManager.getLogger(CheckSortinginGrid.class);

    public static void main(String[] args) {

        logger.info("🚀 Starting Test Case Execution: Login and Edit Quote");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications");
        driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get(""); //url is  hidden due to privacy
        driver.findElement(By.id("username")).sendKeys(""); //username is  hidden due to privacy
        driver.findElement(By.id("password")).sendKeys("");// Password is  hidden due to privacy
        driver.findElement(By.name("Login")).submit();
        logger.info("✅ Login submitted");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Actions action = new Actions(driver);

        WebElement quoteTab = driver.findElement(By.xpath("//a[@title='VCT Quotes']"));
        js.executeScript("arguments[0].click();", quoteTab);
        logger.info("✅ Navigated to 'VCT Quotes' tab");

        WebElement quote = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@title='QT-000101799']")));
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
            Thread.sleep(3000); // wait for grid to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ✅ Click the "Name" column header to sort
        WebElement nameHeader = driver.findElement(By.xpath("//th[@id='qtGrid_Name']"));
        action.doubleClick(nameHeader).perform();
        logger.info("✅ Clicked on 'Name' column header for sorting");

        try {
            Thread.sleep(2000); // wait for sorting to reflect in UI
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ✅ Fetch all rows in the grid
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='qtGrid']//tr[@tabindex='-1']"));
        List<String> actualNameList = new ArrayList<>();

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (columns.size() > 1) {
                actualNameList.add(columns.get(1).getText().trim()); // column[1] = Name
            }
        }

        // ✅ Prepare sorted copy for comparison
        List<String> expectedSortedList = new ArrayList<>(actualNameList);
        Collections.sort(expectedSortedList); // Ascending order

        // ✅ Compare actual vs expected
        if (actualNameList.equals(expectedSortedList)) {
            System.out.println("✅ Name column is sorted in ascending order.");
        } else {
            System.out.println("❌ Name column is NOT sorted correctly.");
            System.out.println("Actual: " + actualNameList);
            System.out.println("Expected: " + expectedSortedList);
        }

        // Optional: Close the browser
        driver.quit();
    }
}

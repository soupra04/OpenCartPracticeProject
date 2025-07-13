package testclasses;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import listeners.RetryAnalyzer;

public class TestAddtoCartOption {

	WebDriver driver;
	Logger logger = LogManager.getLogger(TestAddtoCartOption.class);

	@Test (retryAnalyzer = RetryAnalyzer.class)

	public void addToCart() {
		// TODO Auto-generated method stub

		logger.info("🚀 Starting Test Case Execution: Login and Edit Quote");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "--disable-notifications");
		driver = new ChromeDriver(options);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get("https://tutorialsninja.com/demo/");

		findElement(By.xpath("//span[@class='caret']")).click();
		findElement(By.xpath("//a[normalize-space()='Login']")).click();
		findElement(By.xpath("//input[@id='input-email']")).sendKeys("soupra.cool+12@gmail.com");
		findElement(By.xpath("//input[@id='input-password']")).sendKeys("12345678");
		findElement(By.xpath("//input[@value='Login']")).submit();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("e");
		findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement searchResult = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='Search - e']")));
		js.executeScript("scrollBy(0, 500)");

		List<WebElement> results = driver.findElements(By.xpath("//div[@class='caption']/h4"));
		
		for (int i = 0; i < results.size(); i++) {
			String productName = results.get(i).getText();
			System.out.println(productName);

			if ("iPod Shuffle".equalsIgnoreCase(productName)) {
				 System.out.println("🎯 Found iPod Shuffle! Clicking...");
				results.get(i).findElement(By.tagName("a")).click();
				break;
				
			}
			
			

		}
		
		WebElement addtoCart = driver.findElement(By.xpath("//button[@id='button-cart']"));
		js.executeScript("arguments[0].scrollIntoView(true);", addtoCart);
		findElement(By.xpath("//input[@id='input-quantity']")).clear();
		findElement(By.xpath("//input[@id='input-quantity']")).sendKeys("2");
		wait.until(ExpectedConditions.elementToBeClickable(addtoCart));
		addtoCart.click();
		
		   // Use retry click for cart-total to avoid StaleElementReferenceException
        clickCartTotalWithRetry(driver, wait);		
		
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table table-striped']")));
		WebElement viewCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='View Cart']")));
		viewCart.click();
		
		WebElement shoppinCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']/h1[contains(normalize-space(.), 'Shopping Cart')]")));
		//input[@name='quantity[270772]']
		
		WebElement qty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[starts-with(@name, 'quantity')]")));
		System.out.println(qty.getAttribute("value"));
		
		if(!qty.getAttribute("value").equalsIgnoreCase("1")) {
			qty.clear();
			qty.sendKeys("1");
			WebElement checkout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='btn btn-primary']")));
			js.executeScript("arguments[0].scrollIntoView(true);", checkout);
			checkout.click();
			
		}
		
		

	}

	private void clickCartTotalWithRetry(WebDriver driver2, WebDriverWait wait) {
		// TODO Auto-generated method stub
		 int attempts = 0;
	        while (attempts < 3) {
	            try {
	                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='cart-total']"))).click();
	                return; // success, exit method
	            } catch (StaleElementReferenceException e) {
	                attempts++;
	                System.out.println("⚠️ StaleElementReferenceException caught while clicking cart-total. Retry attempt " + attempts);
	                // Optional: Thread.sleep(500); // add a small wait before retrying if needed
	            }
	        }
	        throw new RuntimeException("Failed to click cart-total after 3 retry attempts due to StaleElementReferenceException");
		
	        
	        
	}
	
	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

}

package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
	
	public static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		DriverManager.driver = driver;
	}
	
	public static void init(String browser) {
		
		if(browser.equalsIgnoreCase("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			driver= new ChromeDriver(chromeOptions);
			
		} else if (browser.equalsIgnoreCase("edge")){
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--start-maximized");
			driver= new EdgeDriver(edgeOptions);
			
		} else if ("firefox".equals(browser)) {
         FirefoxOptions firefoxOptions = new FirefoxOptions();
         firefoxOptions.addArguments("--start-maximized");
         driver = new FirefoxDriver(firefoxOptions);
     } else {
			System.out.println("No browser found");
		}
	}
	
	public static void tearDown() {
		if(driver != null) {
		//driver.quit();
	}
	}
	

}

package testclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGridStandaloneTest {

    public static void main(String[] args) {
        try {
            // Hub URL for standalone setup
            String hubUrl = "http://localhost:4444/wd/hub";

            // Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized", "--disable-notifications");

            // Create RemoteWebDriver
            WebDriver driver = new RemoteWebDriver(new URL(hubUrl), options);

            // Navigate to your target URL
            driver.get(""); //url is  hidden due to privacy

            // Example: Print title
            System.out.println("Page Title: " + driver.getTitle());

            // Quit browser
            driver.quit();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

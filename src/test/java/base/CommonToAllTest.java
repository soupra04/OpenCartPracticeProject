package base;

import org.apache.logging.log4j.LogManager; //log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import driver.DriverManager;

public class CommonToAllTest {
	public static Logger logger; //log4j
	
	@BeforeMethod (groups = "sanity")
	@Parameters("browser") // 👈 Accept 'browser' parameter from testng.xml
	public void setup(@Optional("chrome") String browser) {
		logger = LogManager.getLogger(this.getClass());
		
		DriverManager.init(browser);
		
	}
	
	@AfterMethod (groups = "sanity")
	public  void tearDown() {
		DriverManager.tearDown();
		
	}

}

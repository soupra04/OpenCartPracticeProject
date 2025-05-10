package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import driver.DriverManager;

public class CommonToAllTest {
	
	@BeforeMethod
	public void setup() {
		DriverManager.init("chrome");
		
	}
	
	@AfterMethod
	public  void tearDown() {
		DriverManager.tearDown();
		
	}

}

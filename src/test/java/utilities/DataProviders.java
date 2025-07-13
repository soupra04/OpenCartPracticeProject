package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	
	//DataProvider 1
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {
		
		String path = ".\\testData\\OpenCart_LoginData.xlsx";
		
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);
		String logindata[][] = new String[totalrows][totalcols];
		for (int i=1;i<=totalrows;i++) 
		{
			for (int j=0;j<totalcols;j++) 
			{
				logindata[i-1][j] =xlutil.CellData("Sheet1",i,j);
				
			}
		}
		return logindata;
		
		
		
	}
	
	
	//DataProvider 2 for my Test
		@DataProvider(name="LoginData2")
		public String[][] getData1() throws IOException {
			
			String path = ".\\testData\\CCLoginTest.xlsx";
			
			ExcelUtility xlutil = new ExcelUtility(path);
			
			int totalrows = xlutil.getRowCount("Sheet1");
			int totalcols = xlutil.getCellCount("Sheet1", 1);
			String logindata[][] = new String[totalrows][totalcols];
			for (int i=1;i<=totalrows;i++) 
			{
				for (int j=0;j<totalcols;j++) 
				{
					logindata[i-1][j] =xlutil.CellData("Sheet1",i,j);
					
				}
			}
			return logindata;
			
			
			
		}
		
		//DataProvider 3 for regestration Test
				@DataProvider(name="LoginData3")
				public String[][] getData2() throws IOException {
					
					String path = ".\\testData\\RegistrationWithExcel.xlsx";
					
					ExcelUtility xlutil = new ExcelUtility(path);
					
					int totalrows = xlutil.getRowCount("Sheet1");
					int totalcols = xlutil.getCellCount("Sheet1", 1);
					String logindata[][] = new String[totalrows][totalcols];
					for (int i=1;i<=totalrows;i++) 
					{
						for (int j=0;j<totalcols;j++) 
						{
							logindata[i-1][j] =xlutil.CellData("Sheet1",i,j);
							
						}
					}
					return logindata;
					
					
					
				}
				
}

package com.practice.hybrid;
import java.util.Hashtable;

import org.junit.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.practice.hybrid.util.Constants;
import com.practice.hybrid.util.Xls_Reader;

public class Keywords {
	
	ExtentTest test;
	//Declare as a public
	AppKeywords app;
	

	public Keywords(ExtentTest test) {
		// TODO Auto-generated constructor stub
		//global variable test = local variable test
		this.test = test;
	}



	//public static void main(String[] args) throws InterruptedException
	public void executeKeywords (
			String testUnderExecution,
			Xls_Reader xls,
			Hashtable<String, String> testData) throws InterruptedException{
		// TODO Auto-generated method stub
		
		/*GenericKeywords app = new GenericKeywords();
		app.openBrowser("Mozilla");
		app.navigate("url");
		app.email("email_id", "sagar.22");
		app.click("click_id");
		app.password("userpass", "222546");
		app.continue1("loginsubmit");*/
		
		//String testUnderExecution ="rediff_money";
		
		//Initialize the GenericKeywords
		 app = new AppKeywords(test);
		
		int rows = xls.getRowCount(Constants.KEYWORD_SHEET);
		//System.out.println("Rows are "+rows);
		
		for(int rNum=2;rNum<=rows;rNum++) {
			String tcid = xls.getCellData(Constants.KEYWORD_SHEET, Constants.TCID_COL, rNum);
			if (tcid.equals(testUnderExecution)) {
			String keyword = xls.getCellData(Constants.KEYWORD_SHEET, Constants.KEYWORD_COL, rNum);
			String object = xls.getCellData(Constants.KEYWORD_SHEET,Constants.OBJECT_COL , rNum);
			String key = xls.getCellData(Constants.KEYWORD_SHEET, Constants.DATA_COL, rNum);
			String data = testData.get(key);
			//test.log(LogStatus.INFO,tcid+"---"+keyword+"---"+object+"---"+data);
			//System.out.println("Excel contents"+tcid+"---"+keyword+"---"+object+"---"+data);
			String result = "";
			if(keyword.equals("openBrowser"))
				result= app.openBrowser(data);
			else if(keyword.equals("navigate"))
				result= app.navigate(object);
			else if (keyword.equals("click"))
			{
				result= app.click(object);
				System.out.println("Test of click"+result);
			}
			else if (keyword.equals("input"))
				result= app.input(object, data);
			else if (keyword.equals("verifyLoginDetails"))
				result= app.verifyLoginDetails(testData);
			//Object is passed to the 
			else if (keyword.equals("verifyElementPresent"))	
				result = app.verifyElementPresent(object);
			//added a new Application keyword for login and sending complete "testData" as it's required for login.
			else if (keyword.equals("flipKartLogin"))
				result = app.flipKartLogin(testData);
			else if (keyword.equals("verifyFlipkartLogin"))
				result = app.verifyFlipKartLogin(testData.get("ExpectedResult"));
			else if (keyword.equals("defaultLogin"))
				result = app.defaultLogin();
			else if (keyword.equals("wait"))
				 app.wait(data);
			else if (keyword.equals("mouseHover"))
				result = app.mouseHover(object);
			else if (keyword.equals("scrollTo"))
				result = app.scrollTo(object);
			else if (keyword.equals("backPage"))
					app.backPage();
			
						
			//Central place to report failure.
			if (!result.equals(Constants.PASS)) {
				app.reportFailure(result);
				Assert.fail(result);
			}
			}
			
		}
		
	}
	//added a function for getting and Ask Pr@D
	public AppKeywords getGenericKeywords() {
		return app;
	}

}




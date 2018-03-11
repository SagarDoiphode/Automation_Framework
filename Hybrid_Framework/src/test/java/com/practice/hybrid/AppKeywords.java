package com.practice.hybrid;

import java.awt.event.WindowStateListener;
import java.util.Hashtable;

import org.openqa.selenium.JavascriptExecutor;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.practice.hybrid.util.Constants;

public class AppKeywords extends GenericKeywords {
	//Why added constructor: 
	public AppKeywords(ExtentTest test) {
		super(test);
		
	}

	public String verifyLoginDetails(Hashtable<String, String> testData) {
		
		String expectedName = testData.get("Name");
		
		return Constants.PASS;
		
		//getElement
	}
	
	public String login (String username, String password) {
		getElement("loginLink_xpath").click();
		getElement("loginLink_xpath").click();
		getElement("userName_xpath").sendKeys(username);
		getElement("password_xpath").sendKeys(password);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getElement("LoginButton_xpath").click();
		
		//You can validate the login details.
		return Constants.PASS;
		
		
	}

	public String flipKartLogin(Hashtable<String, String> testData) {
		test.log(LogStatus.INFO, "Logging in with "+testData.get("Username")+"/"+testData.get("Password"));
		return login(testData.get("userName"), testData.get("passWord"));
		
		
	}
	
	public String defaultLogin() {
		return login(prop.getProperty("userName"), prop.getProperty("passWord"));
	}

	public String verifyFlipKartLogin(String expectedResult) {
		test.log(LogStatus.INFO, "Validating Login for TestCase#1");
		boolean result = isElementPresent("myAccount_xpath");
		String actualResult = "";
		if(result)
			actualResult ="Success";
		else
			actualResult ="Failure";
		System.out.println("actualResult--"+actualResult+"expectedResult--"+expectedResult);
		if(!actualResult.equals(expectedResult)) {
			return "Failed - "+"Got actual result as  "+actualResult;
		//No need to write down the Assert for failure as already covered in Keyword class.
		}
		else
		return Constants.PASS;		
	}

	public String scrollVertically(String key) {
		test.log(LogStatus.INFO, "Scrolling Vertically "+ key);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("windows.scrollTo(0,"+key+")");
		 	return Constants.PASS;
	}	
}
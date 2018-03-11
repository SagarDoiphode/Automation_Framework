package com.practice.hybrid.sessionsuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.practice.hybrid.Keywords;
import com.practice.hybrid.util.Constants;
import com.practice.hybrid.util.ExtentManager;
import com.practice.hybrid.util.Xls_Reader;

public class LoginTest {
	ExtentTest test;
	//Need to understand this from Pradnya
	ExtentReports rep = ExtentManager.getInstance();
	Xls_Reader xls = new Xls_Reader(Constants.SessionSuite_XLS);
	Keywords app;
	String testName = "LoginTest";
	/**
	 * 
	 * @param data
	 * @throws InterruptedException
	 */
	@Test(dataProvider="getData")
	public void loginTest(Hashtable<String, String> data) throws InterruptedException {
		test = rep.startTest(testName);
		//We can put complete data in the test log.
		test.log(LogStatus.INFO, data.toString());
		
		if(com.practice.hybrid.util.DataUtil.isSkip(xls, testName) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Test case is skipped as Runmode is N");
			throw new SkipException("Test case is skipped as Runmode is N");
		}
		
		test.log(LogStatus.INFO, "Logged in to FlipKart");
		//Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//data//SuiteA.xlsx");
		test.log(LogStatus.INFO, "Started executing Keywords");
		//Here the test execution has been started and passover test reference in the constructor. 
		app = new Keywords(test);
		
		app.executeKeywords("LoginTest", xls, data);
		//Add screen shot
		//test.log(LogStatus.PASS, "Test Passed");
		
		//called report failure function.
		//app.getGenericKeywords().reportFailure("Failed the test case in Rediff_Money");
		//U can call it at test case as well.
		//test.log(LogStatus.INFO, "PASS");
		app.getGenericKeywords().takeScreenShot();
	}
	@AfterTest
	public void quit() {
		if (rep!=null) {
			rep.endTest(test);
			rep.flush();
		}
		//quit
		//Why app is not null.
		if(app!=null) 
		app.getGenericKeywords().closeBrowser();
		test.log(LogStatus.INFO, "Closing Browser");
	}
	
	
	@DataProvider
	public Object[][] getData(){
	
		return com.practice.hybrid.util.DataUtil.getData(xls, testName);
	}
}

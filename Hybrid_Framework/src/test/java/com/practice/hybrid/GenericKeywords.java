package com.practice.hybrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.practice.hybrid.util.Constants;


public class GenericKeywords {
	//Declare WebDriver as global variable.
	public WebDriver driver;
	//property file
	public Properties prop;
	ExtentTest test;
	
	public GenericKeywords(ExtentTest test) {
		
		this.test = test;
		//Create an object of property file
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//sources//project.properties");
			prop.load(fs);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
	}
		public String openBrowser(String browserType) {
		
			test.log(LogStatus.INFO, "Open_Browser"+browserType);
			if (browserType.equals("Mozilla")) {
				System.setProperty("webdriver.gecko.driver", "H:\\Softwares\\geckodriver.exe");
				 driver = new FirefoxDriver();	
			}else if (browserType.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "H:\\Softwares\\chromedriver.exe");
				driver = new ChromeDriver();	
			}else if (browserType.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "H:\\Softwares\\chromedriver.exe");
				driver = new ChromeDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
			return Constants.PASS;
	   }
		public String navigate(String url){
			test.log(LogStatus.INFO, "Navigating to website "+ prop.getProperty(url));
			driver.get(prop.getProperty(url));
			System.out.println("URL is : "+prop.getProperty(url));
			return Constants.PASS;
			
			
		}
		public String click(String locatorType){
			
			//driver.findElement(By.id(locatorType)).click();
			test.log(LogStatus.INFO, "Click and move further"+prop.getProperty(locatorType));
			System.out.println("Click and move further  "+prop.getProperty(locatorType));
			WebElement e = getElement(locatorType);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.click();
			return Constants.PASS;
			
		}
		
		public String input(String locatorType, String data) {
			/*//driver.findElement(By.id(locatorType)).sendKeys(data);
			//System.out.println("email: "+prop.getProperty("email_id"));
			String window = driver.getWindowHandle();
			driver.switchTo().window(window);*/
			test.log(LogStatus.INFO, "Entered Email id :"+prop.getProperty(data));
			WebElement e = getElement(locatorType);
			e.sendKeys(data);
			return Constants.PASS;
			
			}
		
		public String mouseHover(String locatorType) {
			WebElement e = getElement(locatorType);
			Actions action = new Actions(driver);
			action.moveToElement(e).build().perform();
			driver.findElement(By.xpath("//a[@title='Car & Bike Accessories']")).click();
			return Constants.PASS;
			
		}
		
		public String scrollTo(String xpathKey){
			int y = getElement(xpathKey).getLocation().y;
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollTo(0,"+(y-200)+")");
			return Constants.PASS;
		}

		public String wait(String timeout) {
			try {
				Thread.sleep(Integer.parseInt(timeout));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return Constants.PASS;
		}
		
	 public void backPage() {
		 driver.navigate().back();
	 }
		//closing browser.
		public String closeBrowser() {
			test.log(LogStatus.INFO, "Closing Browser");
			driver.quit();
			return Constants.PASS;
			
		}
		
		/**************Validation Keywords*********************************************/
		
		public String verifyText(String locatorType, String expectedText) {
			WebElement e = getElement(locatorType);
			String actualText = e.getText();
			if (actualText.equals(expectedText))
				return Constants.PASS;
			else 
				return Constants.FAIL;
			
		}
		
		public String verifyElementPresent(String locatorKey){
			boolean result = isElementPresent(locatorKey);
			//result value would be returned from the isElementPresent function and it can be True or False.
			
			if (result)
				
				return Constants.PASS+"Element found for verifyElementPresent function";
			else 
				return Constants.FAIL+"Element could not found"+locatorKey;
						
		}
		
		public String verifyElementNotPresent(String locatorKey){
			boolean result = isElementPresent(locatorKey);
			//result value would be returned from the isElementPresent function and it can be True or False.
			if (!result)
				return Constants.PASS;
			else 
				return Constants.FAIL+"Element could found"+locatorKey;
						
		}
		
		//*****************************Utility Function**********************************
		public WebElement getElement(String locatorType) {
			WebElement e = null;
			try {
			if (locatorType.endsWith("_id")) 
				e = driver.findElement(By.id(prop.getProperty(locatorType)));
				else if(locatorType.endsWith("_xpath"))
				e =	driver.findElement(By.xpath(prop.getProperty(locatorType)));
				else if(locatorType.endsWith("_name"))
				e = driver.findElement(By.name(prop.getProperty(locatorType)));
			}catch (Exception ex) {
				//Whenever want to fail message
				reportFailure("Failure in Element Extraction - "+locatorType);
				test.log(LogStatus.FAIL, "Failure in the element is :"+locatorType);
				//Assert.fail("");
			}
			return e;
			
		}
		
		public boolean isElementPresent(String locatorType){
			List<WebElement> e = null;
			if (locatorType.endsWith("_id")) 
				e = driver.findElements(By.id(prop.getProperty(locatorType)));
				else if(locatorType.endsWith("_xpath"))
				e =	driver.findElements(By.xpath(prop.getProperty(locatorType)));
				else if(locatorType.endsWith("_name"))
				e = driver.findElements(By.name(prop.getProperty(locatorType)));
			if (e.size()==0)
				return false;
			else
				return true;
			}
			
		
		/**************************Reporting function****************************************************/
		
		public void reportFailure(String failureMessage) {
			takeScreenShot();
			test.log(LogStatus.INFO, failureMessage);
			
			
		}
		
		public void takeScreenShot() {
			//columns 12
			Date d = new Date();
			String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
			String path=Constants.SCREENSHOT_PATH+screenshotFile;
			// take screenshot
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//embedded with test report
			test.log(LogStatus.INFO, test.addScreenCapture(path));
			
		}
}



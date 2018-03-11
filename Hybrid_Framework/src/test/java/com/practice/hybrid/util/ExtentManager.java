package com.practice.hybrid.util;

	//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html


	import java.io.File;
	import java.util.Date;

	import com.relevantcodes.extentreports.DisplayOrder;
	import com.relevantcodes.extentreports.ExtentReports;

	public class ExtentManager {
		private static ExtentReports extent;

		public static ExtentReports getInstance() {
			if (extent == null) {
				
				Date d=new Date();
				String fileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
				//extent = new ExtentReports(Constants.REPORT_PATH+fileName, true, DisplayOrder.NEWEST_FIRST);
				//location of the report generated.
				extent = new ExtentReports(Constants.REPORT_PATH+fileName, true, DisplayOrder.NEWEST_FIRST);
				//Config file for extent report
				extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
				// optional
				extent.addSystemInfo("Selenium Version", "3.8.1").addSystemInfo(
						"Environment", "QA");
			}
			return extent;
		}
	}



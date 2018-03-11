package com.practice.hybrid.util;

import java.util.Hashtable;

public class DataUtil {
	//getData will be called for any particular test case. Hard coding 
	public static Object[][] getData(Xls_Reader xls, String testName){
		
		String sheetName = "Data";
		//String testCaseName = "TestA";
		
		int testStartRowNum= 1;
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(testName)){
			testStartRowNum++;
			
		}
		System.out.println("Test Start Row Num"+testStartRowNum);
		
		int colStartRowNum = testStartRowNum+1;
		int dataStartRowNum = testStartRowNum+2;
		
		//how many rows of the column
		
		int rows=1;
		while(!xls.getCellData(sheetName,0, dataStartRowNum+rows).equals("")){
			rows++;
		}
		System.out.println("Rows are ==="+rows);
		
		//total columns
		
		int cols=0;
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")){
			cols++;
			
		}
		
		System.out.println(cols);
		//create two dimensional object array
		//Column is only one 	
		Object[][] data = new Object[rows][1];
		//read the data
		int dataRow=0;
		
		Hashtable<String, String> table = null; 
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++) {
			//new table is start for new row
			table = new Hashtable<String, String>();
			//When we start a new row every row is a new HashTable
			for (int cNum=0;cNum<cols;cNum++) {
				//name of the key is column
				String key = xls.getCellData(sheetName, cNum, colStartRowNum);
				//value is the actual test data
				String value = xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
			}
			//table value in the two dimensional array
			//Columns is zero as HashTable having only one column
			data[dataRow][0] = table;
			dataRow++;
		}
		
		return data;
		
	}
	//This function returns true if TCID Runmode is Y
	//this function returns false if TCID Runmode is N
	public static boolean isSkip(Xls_Reader xls, String testName) {
		
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		
		for (int rNum=2;rNum<=rows;rNum++) {
			String tcid= xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rNum);
			if (tcid.equals(testName)) {
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				if (runmode.equals("Y"))
					return false;
				else
					return true;
			}
			
		}
		
		//if still we are not able to find the test case name then it will skip the test case
		return true; //default
	}
}

	



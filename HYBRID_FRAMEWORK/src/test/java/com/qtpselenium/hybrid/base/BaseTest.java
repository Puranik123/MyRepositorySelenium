package com.qtpselenium.hybrid.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import com.qtpselenium.hybrid.keywords.Keywords;
import com.qtpselenium.hybrid.utility.DataUtility;
import com.qtpselenium.hybrid.utility.ExtentManager;
import com.qtpselenium.hybrid.utility.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseTest {
	public ExtentReports rep=ExtentManager.getInstance();
	protected ExtentTest test;
	public Keywords ap;
	public Xls_Reader read;
	public String testName;
	
	@AfterMethod
	public void EndingTest(){
		if(rep!=null){
		rep.endTest(test);
		rep.flush();
	}	
		//quit
		//if(ap!=null){
				//ap.getGenericKeywords().CloseBrowser();
		//}
	}

	@DataProvider
	public Object[][] getData(){

	return DataUtility.getData(testName, read);

	}

}
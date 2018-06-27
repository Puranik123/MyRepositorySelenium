package com.qtpselenium.hybrid.sessionsuite;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.hybrid.base.BaseTest;
import com.qtpselenium.hybrid.keywords.Keywords;
import com.qtpselenium.hybrid.utility.Constants;
import com.qtpselenium.hybrid.utility.DataUtility;
import com.qtpselenium.hybrid.utility.ExtentManager;
import com.qtpselenium.hybrid.utility.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ChangePasswordTest extends BaseTest{
	
	
	@BeforeTest
	public void init(){
		testName="ChangePassword";
		read=new Xls_Reader(Constants.SessionSuite_XLS);
	}	
	@Test(dataProvider="getData")
	public void ChangePassword(Hashtable<String,String> data) throws IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		test=rep.startTest(testName);
		test.log(LogStatus.INFO, "Starting test "+testName);
		test.log(LogStatus.INFO, data.toString());
	    
		if(DataUtility.IsRunnable(testName, read) || data.get("Runmode").equals("N")){
		test.log(LogStatus.SKIP, "Skipping this test as value is N ");
		throw new SkipException("Skipping this test as value is N");
		};
		
		//System.out.println(n.get("Username")+"---"+n.get("Password"));
		
	 ap=new Keywords(test);
		test.log(LogStatus.INFO, "Executing Keywords");
		ap.ExecuteKeywords(testName, read,data);
		//add screenshot
		//ap.getGenericKeywords().ReportFailure("xxxx");
		ap.getGenericKeywords().TakeScreenshot();
		
		test.log(LogStatus.PASS, "Test is passed");
		
	}


	
	
}

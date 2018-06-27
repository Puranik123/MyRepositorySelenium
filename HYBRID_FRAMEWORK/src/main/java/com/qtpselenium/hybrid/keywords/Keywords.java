package com.qtpselenium.hybrid.keywords;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;





import com.google.common.reflect.Reflection;
import com.qtpselenium.hybrid.utility.Constants;
import com.qtpselenium.hybrid.utility.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;

public class Keywords {
ExtentTest test;
//GenericKeywords app;
AppKeywords app;

public Keywords(ExtentTest test){
this.test=test;
}
//public static void main(String[] args) throws InterruptedException, IOException{
public void ExecuteKeywords(String TestUnderExecution,Xls_Reader read,Hashtable<String,String> data) throws IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
/*obj=new GenericKeywords();
obj.OpenBrowser("Brows");
obj.navigate("url");
obj.click("Gmail_linktext");
obj.click("SignIn_linktext");
obj.type("username_xpath", "sankalp.puranik");
//Thread.sleep(5000);
obj.click("NextButtonUsername_xpath");
Thread.sleep(5000);
obj.type("password_xpath", "1980march");
obj.click("NextButtonPassword_xpath");*/

//String TestUnderExecution="GmailTest";
	
app=new AppKeywords(test);
//app.ReportFailure("xxxx");
//Xls_Reader xls=new Xls_Reader(Constants.SUITEA_XLS);
int rows=read.getRowCount(Constants.KEYWORDS_SHEET);

System.out.println("Total rows are "+rows);



for(int rnum=2;rnum<=rows;rnum++){
	
	String TCID=read.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COLUMN, rnum);
	if(TCID.equals(TestUnderExecution)){
		String Keyword=read.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COLUMN, rnum);
		String Object=read.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COLUMN, rnum);
		String key=read.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COLUMN, rnum);
		String Data=data.get(key);
		
		test.log(LogStatus.INFO,TCID+"----"+Keyword+"---"+Object+"---"+Data);
		String result="";
		
		/*Method method;
		try{
		method=app.getClass().getMethod(Keyword);
		method.invoke(app);
		}catch(Exception e){
			e.printStackTrace();
		}*/
	if(Keyword.equals("OpenBrowser")){
		result=app.OpenBrowser(Data);
	}else if(Keyword.equals("navigate")){
		result=app.navigate(Object);
	}else if(Keyword.equals("click")){
		result=app.click(Object);
	}else if(Keyword.equals("type")){
		Thread.sleep(5000);
		result=app.type(Object, Data);
	}else if(Keyword.equals("CloseBrowser")){
		result=app.CloseBrowser();
	}else if(Keyword.equals("VerifyLoginTest")){
		result=app.VerifyLoginDetails(data);
	}else if(Keyword.equals("VerifyElementPresent")){
		result=app.VerifyElementPresent(Object);
	}else if(Keyword.equals("FlipKartLogin")){
		result=app.FlipKartLogin(data);
	}else if(Keyword.equals("VerifyFlipKartLogin")){
		result=app.VerifyFlipKartLogin(data.get("ExpectedResult"));
	}else if(Keyword.equals("DefaultLogin")){
		result=app.DefaultLogin();
	}else if(Keyword.equals("scroll")){
		result=app.scroll(Object);
	}else if(Keyword.equals("filterMobileAndValidate")){
		result=app.filterMobileAndValidate(data);
	}else if(Keyword.equals("SearchMobileAndSelect")){
		result=app.SearchMobileAndSelect(data);
	}
	
	//central place to report failure
	/*if(!result.equals(Constants.PASS)){
		app.ReportFailure(result);
}*/
	//String result="";
	/*switch (Keyword){
	
	case "OpenBrowser":
		result=app.OpenBrowser(Data);	
	break;
	
	case "navigate":
		result=app.navigate(Object);	
		break;
	
	case "click":
		result=app.click(Object);	
		break;
	
	case "type":
		Thread.sleep(5000);
		result=app.type(Object, Data);
		break;
		
	case "CloseBrowser":
		Thread.sleep(5000);
		result=app.CloseBrowser();
		break;	
	
	case "VerifyLoginTest":	
		//name and email
		//app.VerifyLoginDetails(Data,Email);
		result=app.VerifyLoginDetails(data);
		break;
		
	case "VerifyElementPresent":
		result=app.VerifyElementPresent(Object);
		
	default:
	   System.out.println("None is matching");
		
			
		if(!result.equals(Constants.PASS)){
			app.ReportFailure(result);
	}
	}*/
		
	}
	
}
}

public AppKeywords getGenericKeywords(){
	return app;
}
}



package com.qtpselenium.hybrid.keywords;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qtpselenium.hybrid.utility.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AppKeywords extends GenericKeywords{
	

public AppKeywords(ExtentTest test) throws IOException {
		super(test);
		
	}

public String VerifyLoginDetails(Hashtable<String, String> data) throws IOException{
//public void VerifyLoginDetails(String data,String Email) throws IOException{
String ExpName=data.get("Name");
String ExpEmail=data.get("Email");

return Constants.PASS;//you can write any other message here
	
}

public String Login(String username,String password) throws IOException{
	GetElement("username_xpath").sendKeys(username);
	GetElement("password_xpath").sendKeys(password);
	GetElement("LoginButton_xpath").click();
	return Constants.PASS;
}

public String FlipKartLogin(Hashtable<String, String> data) throws IOException {
	test.log(LogStatus.INFO, "Logging in with "+data.get("Username ")+data.get("Password"));
	return Login(data.get("Username"),data.get("Password"));
	
}

public String DefaultLogin() throws IOException{
return Login(prop.getProperty("Username"),prop.getProperty("Password"));	
}

public String VerifyFlipKartLogin(String ExpectedResult) throws IOException, InterruptedException {
	test.log(LogStatus.INFO, "Validating Login");
	Thread.sleep(5000);
	boolean result=IsElementPresent("MyAccount_xpath");
	String ActualResult="";
	if(result){
		ActualResult="Success";
	}else{
		ActualResult="Failure";
	}
	//Assert.assertEquals(ActualResult, ExpectedResult);
	if(!ActualResult.equals(ExpectedResult)){
		//return "Got actual result as "+ActualResult;
		test.log(LogStatus.FAIL, "Got actual result as "+ActualResult);
		Assert.fail("Got actual result as "+ActualResult);
	}
return Constants.PASS;
}

public String scroll(String locator) throws IOException, InterruptedException {
	Thread.sleep(5000);
	//WebElement r=GetElement(locator);
	GetElement(locator).click();
	//JavascriptExecutor obj=(JavascriptExecutor)driver;
	//obj.executeScript("arguments[0].scrollTo(0,150");
	/*obj.executeScript("arguments[0].scrollIntoView(true)", r);
	obj.executeScript("arguments[0].click()", r);*/
	return Constants.PASS;
}

public String filterMobileAndValidate(Hashtable<String, String> data) throws IOException, InterruptedException {
	String brandnamexpath=data.get("MobileCompany");
	//driver.findElement(By.xpath("//div[@class='"+brandname+"']")).click();
	Thread.sleep(8000);
	GetElement(brandnamexpath).click();
	Thread.sleep(8000);
	//validate names
	List<WebElement> mobiles=driver.findElements(By.xpath(prop.getProperty("AllMobiles_xpath")));
	for(int i=0;i<mobiles.size();i++){
		System.out.println(mobiles.get(i).getText());
		if(!mobiles.get(i).getText().contains("Samsung")){
			
			return Constants.FAIL+"Found the mobile "+mobiles.get(i).getText();
		}
	}
	
	//validate prices
WebElement m=GetElement("Min_xpath");
//WebElement m=driver.findElement(By.xpath("//section[@class='_1OmDPc _2Zm4Qh']/div[4]/div[@class='_1QaKk1']/select"));
//WebElement n=driver.findElement(By.xpath("//section[@class='_1OmDPc _2Zm4Qh']/div[4]/div[@class='_3nSxeA']/select"));
WebElement n=GetElement("Max_xpath");
Select min=new Select(m);

String PriceRange=data.get("PriceRange");
//String MinPrice=data.get("MinPrice");
//String MaxPrice=data.get("MaxPrice");
String value[]=PriceRange.split(" ");
String MinValue=value[0];
String MaxValue=value[2];
//System.out.println("Minimum value is "+value[0]);
min.selectByValue(MinValue);

Select max=new Select(n);
max.selectByValue(MaxValue);
	
	
return Constants.PASS;
}

public String SearchMobileAndSelect(Hashtable<String, String> data) throws InterruptedException, IOException {
	String item=data.get("ItemName");
	boolean found=false;
	List<WebElement> TyreBeforeScroll=null;
	List<WebElement> TyreAfterScroll=null;
	int index=-1;
	while(!found){
		Thread.sleep(5000);
		
		TyreBeforeScroll=driver.findElements(By.xpath(prop.getProperty("AllTyres_xpath")));	
	  int y_last=TyreBeforeScroll.get(TyreBeforeScroll.size()-1).getLocation().y;
	
	JavascriptExecutor exec=(JavascriptExecutor)driver;
	exec.executeScript("window.scrollBy(0,"+y_last+")");
	Thread.sleep(5000);
	
	TyreAfterScroll=driver.findElements(By.xpath(prop.getProperty("AllTyres_xpath")));
	if(TyreBeforeScroll.size()==TyreAfterScroll.size()){
		return "Tyres not FOUND";
	}
	
	for(int i=0;i<TyreBeforeScroll.size();i++){
		if(TyreBeforeScroll.get(i).getText().contains(item)){
			//found
			index=i;
			System.out.println(TyreBeforeScroll.get(i).getText());
		found=true;
		}
	}
	}

	//found
	int y=TyreBeforeScroll.get(index).getLocation().y;
	JavascriptExecutor e=(JavascriptExecutor) driver;
	e.executeScript("window.scrollBy(0,"+y+")");
	TyreBeforeScroll.get(index).click();
	String ItemText=GetElement("TyreHeading_xpath").getText();
		
	if(!ItemText.contains(item)){
		return "Item name did not match "+ItemText;
	}
	GetElement("AddToCart_xpath").click();
	return Constants.PASS;
}

}


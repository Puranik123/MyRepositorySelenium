package com.qtpselenium.hybrid.keywords;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.google.common.io.Files;
import com.qtpselenium.hybrid.utility.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenericKeywords {
	public WebDriver driver;
	public static Properties prop;
	ExtentTest test;
	boolean grid = true;

	public GenericKeywords(ExtentTest test) throws IOException {
		this.test = test;
		prop = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\resources\\Configuration.properties");
		prop.load(ip);
	}

	public String OpenBrowser(String browser) throws InterruptedException, IOException{
//System.out.println("Opening browser "+prop.getProperty(browser));
test.log(LogStatus.INFO, "Opening browser "+browser);
System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\geckodriver-v0.18.0-win64\\geckodriver.exe");		
System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver_win32\\chromedriver.exe");
if(!prop.getProperty("grid").equals("Y")){//!grid){
if(browser.equals("Mozilla")){
	driver=new FirefoxDriver();
}else if(browser.equals("Chrome")){
	driver=new ChromeDriver();
}else if(browser.equals("IE")){
	driver=new InternetExplorerDriver();	
}

/*}else{
	DesiredCapabilities cap=null;
	if(browser.equals("Mozilla")){
	cap = DesiredCapabilities.firefox();
	cap.setBrowserName("firefox");
	cap.setJavascriptEnabled(true);
	cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);

	}else if(browser.equals("Chrome")){
	cap = DesiredCapabilities.chrome();
	cap.setBrowserName("chrome");
	cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	}

	try {
	driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
	} catch (Exception e) {
	e.printStackTrace();
	}*/
}else{
	//grid run
	DesiredCapabilities cap=null;
	if(browser.equals("Mozilla")){
	cap=DesiredCapabilities.firefox();
	cap.setBrowserName("firefox");
	cap.setJavascriptEnabled(true);
	cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	}else if(browser.equals("Chrome")){
	cap=DesiredCapabilities.chrome();	
	cap.setBrowserName("chrome");
	cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	}
driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);

}
return Constants.PASS;
	}
	
	
	public String navigate(String url) {
		test.log(LogStatus.INFO, "Navigating to " + prop.getProperty(url));
		driver.get(prop.getProperty(url));
		return Constants.PASS;
	}

	public String type(String locator, String data) throws IOException {
		test.log(LogStatus.INFO, "Typing in " + prop.getProperty(locator));
		GetElement(locator).sendKeys(new String(data));
		return Constants.PASS;
	}

	public String click(String locator) throws IOException,
			InterruptedException {
		test.log(LogStatus.INFO, "Clicking on " + prop.getProperty(locator));
		Thread.sleep(8000);
		GetElement(locator).click();
		return Constants.PASS;
	}

	public String CloseBrowser() {
		// driver.quit();
		test.log(LogStatus.INFO, "Closing browser");
		return Constants.PASS;
	}

	/*************** Utility function ******************/

	public WebElement GetElement(String locator) throws IOException {
		WebElement e = null;
		try {
			if (locator.endsWith("_linktext")) {
				e = driver.findElement(By.linkText(prop.getProperty(locator)));
			} else if (locator.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(prop.getProperty(locator)));
			} else if (locator.endsWith("_id")) {
				e = driver.findElement(By.id(prop.getProperty(locator)));
			} else if (locator.endsWith("_name")) {
				driver.findElement(By.name(prop.getProperty(locator)));
			}
		} catch (Exception f) {
			test.log(LogStatus.FAIL, "Locator is incorrect " + locator);

			ReportFailure("Locator is incorrect " + locator);

		}
		return e;
	}

	public boolean IsElementPresent(String locatorkey) throws IOException {
		List<WebElement> e = null;
		try {
			if (locatorkey.endsWith("_linktext")) {
				e = driver.findElements(By.linkText(prop
						.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("_xpath")) {
				e = driver.findElements(By.xpath(prop.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("_id")) {
				e = driver.findElements(By.id(prop.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("_name")) {
				driver.findElements(By.name(prop.getProperty(locatorkey)));
			}
		} catch (Exception f) {
			test.log(LogStatus.FAIL, "Locator is incorrect " + locatorkey);

			ReportFailure("Locator is incorrect " + locatorkey);
		}

		if (e.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public String TakeScreenshot() throws IOException {
		// take screenshot
		Date d = new Date();
		String screenshotfile = d.toString().replace(' ', '_')
				.replace(':', '_')
				+ ".JPEG";
		String path = Constants.SCREENSHOT_PATH + screenshotfile;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(scrFile, new File(path));

		// adding screenshots to reports
		test.log(LogStatus.INFO, test.addScreenCapture(path));
		return Constants.PASS;
	}

	/************** Reporting ***************/

	public String ReportFailure(String failmessage) throws IOException {
		TakeScreenshot();
		test.log(LogStatus.FAIL, failmessage);
		Assert.fail(failmessage);
		return Constants.PASS;
	}

	/******************* Validation Keywords **********************/
	public String VerifyText(String locator, String ExpectedText)
			throws IOException {
		String ActualText = GetElement(prop.getProperty(locator)).getText();
		String ExpText = ExpectedText;
		if (ActualText.equals(ExpText)) {
			return Constants.PASS;
		} else {
			return Constants.FAIL;
		}
	}

	public String VerifyElementPresent(String locator) throws IOException {
		boolean result = IsElementPresent(locator);

		if (result) {
			return Constants.PASS;
		} else {
			return Constants.FAIL + " Unable to find element " + locator;
		}
	}

	public String VerifyElementNotPresent(String locator) throws IOException {
		boolean result = IsElementPresent(locator);

		if (!result) {
			return Constants.PASS;
		} else {
			return Constants.FAIL + "Able to find element " + locator;
		}
	}
}

package com.qtpselenium.hybrid.sessionsuite;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
final class Flipkart {
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
	System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\geckodriver-v0.18.0-win64\\geckodriver.exe");
	 driver=new FirefoxDriver();	
	driver.get("http://flipkart.com");
	//Thread.sleep(5000);
	
	//driver.findElement(By.linkText("Login & Signup")).click();
	ClosePopIfPresent();
	driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div/div[2]/div/form/div[1]/input")).sendKeys("9892719717");
	driver.findElement(By.xpath("//input[@type='password']")).sendKeys("24march");
	
	}

	public static void ClosePopIfPresent() throws InterruptedException{
		Set<String> handle=driver.getWindowHandles();
		System.out.println("Total windows opened are "+handle.size());
		Iterator<String> s=handle.iterator();
		if(handle.size()>1){
			Iterator<String> it=handle.iterator();
			String main=it.next();
			String pop=it.next();
			System.out.println("Handle of main window is "+main);
			driver.switchTo().window(pop);
			Thread.sleep(5000);
			driver.close();
			driver.switchTo().window(main);
			}	
		
	}
}

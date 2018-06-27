package com.qtpselenium.hybrid.base;

import java.io.IOException;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

public class Parallel_Execution {

	public static void main(String args[]) throws IOException{
TestNG testng=new TestNG();
testng.setXmlSuites((List<XmlSuite>)(new Parser(System.getProperty("user.dir")+"\\src\\test\\resources\\testng.xml").parse()));
testng.setSuiteThreadPoolSize(3);	
testng.run();
	}
}

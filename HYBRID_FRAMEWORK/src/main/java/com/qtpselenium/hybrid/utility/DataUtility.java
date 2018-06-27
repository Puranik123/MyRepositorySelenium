package com.qtpselenium.hybrid.utility;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;

public class DataUtility {


public static Object[][] getData(String TestCaseName,Xls_Reader read){
	String Sheet="Data";
	
	int rowcount=read.getRowCount("Data");
	System.out.println("Total number of rows are "+rowcount);

	int TestStartRowNumber=1;


	while(!read.getCellData(Sheet, 0, TestStartRowNumber).equals(TestCaseName)){
	TestStartRowNumber++;
	}
	int ColStartRowNumber=TestStartRowNumber+1;
	int DataStartRowNumber=TestStartRowNumber+2;
	System.out.println("Test start from row number is "+TestStartRowNumber);
	System.out.println("Column start row number is "+ColStartRowNumber);
	System.out.println("Data start row number is "+DataStartRowNumber);

	//calculate rows of data

	int rows=0;

	while(!read.getCellData(Sheet, 0, DataStartRowNumber+rows).equals("")){
	rows++;
	}
	System.out.println("Total rows of data are "+rows);

	//calculate columns of data
	int cols=0;
	while(!read.getCellData(Sheet, cols, ColStartRowNumber).equals("")){
	cols++;
	}
	System.out.println("Total columns of data are "+cols);	
	Object[][] n=new Object[rows][1];

	int getrow=0;
	Hashtable<String,String> table=null;
	for(int RNum=DataStartRowNumber;RNum<DataStartRowNumber+rows;RNum++){
	table=new Hashtable();
	for(int CNum=0;CNum<cols;CNum++){

	String key=read.getCellData(Sheet, CNum, ColStartRowNumber);
	String value=read.getCellData(Sheet, CNum, RNum);
	table.put(key, value);
	}
	n[getrow][0]=table;
	getrow++;
	}
return n;
}

//true-N
//false-Y
public static boolean IsRunnable(String TestName,Xls_Reader read){
	int rows=read.getRowCount(Constants.TESTCASES_SHEET);
	
	for(int r=2;r<=rows;r++){
		String tcid=read.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COLUMN, r);
		if(TestName.equals(tcid)){
		String runmode=read.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_NAME, r);
		if(runmode.equals("N")){
			return true;
		}else{
			return false;
		}
		}
}
	return true;//default
}
}

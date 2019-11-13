package com.demo.extentreports;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reporting {

	@Test
	public void loginTest() throws IOException {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/sample1.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		ExtentTest logger=  extent.createTest("LoginTest");
		logger.log(Status.INFO, "Login to amazon");
		logger.log(Status.PASS, "Login to amazon successfully");
		
		//To create multiple test 
		ExtentTest logger1=  extent.createTest("LogOffTest");
		logger1.log(Status.INFO, "Logoff to amazon");
		logger1.log(Status.FAIL, "Logout failed");
		//this screenshot will come in right side with timestamp but image is not coming in proper way
		//logger1.fail("Failed becuase of some issues", MediaEntityBuilder.createScreenCaptureFromPath("E:\\PhotonWS\\ExtentReportV3Practise\\Screenshots\\failure1.png").build());
		
		// adding screenshots to test
		logger1.fail("details").addScreenCaptureFromPath("E:\\PhotonWS\\ExtentReportV3Practise\\Screenshots\\failure1.png");
		extent.flush();
		
		
		
		
	}
	
}

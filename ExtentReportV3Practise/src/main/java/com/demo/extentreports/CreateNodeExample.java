package com.demo.extentreports;

import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class CreateNodeExample {
	
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReports;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest parentTest;
	public static ExtentTest childTest;
	
	String fileName = System.getProperty("user.dir")+"/Reports/HTMLResult.html";
	
	@BeforeTest
	public void setUp() {
		htmlReports = new ExtentHtmlReporter(fileName);
		extent = new ExtentReports();
		extent.attachReporter(htmlReports);
		htmlReports.config().setReportName("Execution Report");
		htmlReports.config().setDocumentTitle("HtmlReportsTestResults");
			
		
	}
	
	@Test(priority =0)
	public void openBrowser() {
		parentTest = extent.createTest("First Test Transaction");
		childTest = parentTest.createNode("Open Application URl" + "\n" + "UniqueId:"+ "123");
		childTest.log(Status.PASS, MarkupHelper.createLabel("Browser is opened by Chrome driver", ExtentColor.BLUE));
		System.setProperty("webdriver.chrome.driver", "E:\\E drive\\Librairies_Selenium\\chromedriver_win32_v77\\chromedriver.exe");
		// Initialize browser
		 driver=new ChromeDriver();
		 driver.navigate().to("https://www.fitternity.com/kamal's-fit-&-fab-fitness-vasanth-nagar");
		childTest.log(Status.PASS, MarkupHelper.createLabel("Application url is opened", ExtentColor.BLUE));
		 //simple comment
		 
	}
	
	@Test(priority =1)
	public void verifyPageTitle(){
		childTest= parentTest.createNode("Verify opened page title");
		childTest.info("Get the page title");
		String actualTitle  = driver.getTitle();
		assertEquals(actualTitle, "Googles");
			
	}
	
	@Test(priority =2)
	public void verifyChildTestPageTitle(){
		test= childTest.createNode("Verify Inside page title");
		test.info("Get the inside page title");
		//String actualTitle  = driver.getTitle();
		//assertEquals(actualTitle, "Googles");
			
	}
	
	@AfterTest
	public void tearDown() {
		extent.flush();
		System.out.println("Execution is completed");
	}
	
	@AfterMethod
	public void checkResults(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{try {
			childTest.log(Status.FAIL, MarkupHelper.createLabel("Test failed becoz of below reason", ExtentColor.RED));
			childTest.log(Status.FAIL, result.getThrowable().getClass().getSimpleName());
			String temp=captureScreenshot(driver);
			
			
				childTest.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		
		}else if(result.getStatus()==ITestResult.SKIP) {
			childTest.log(Status.FAIL, result.getThrowable().getClass().getSimpleName());
		}/*else  {
			childTest.info("Test Case is passed");
		}*/
	}
	
	public static String captureScreenshot(WebDriver driver)
	{
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File src= screenshot.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/Screenshots/"+System.currentTimeMillis()+".png";
		File dest = new File(path);
		try {
			FileUtils.copyFile(src,dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return path;
	}
	

}


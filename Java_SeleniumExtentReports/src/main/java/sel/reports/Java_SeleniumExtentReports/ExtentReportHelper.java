package sel.reports.Java_SeleniumExtentReports;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public abstract class ExtentReportHelper
{

	private static String currentDirectory,reportFileSavePath;
	private static LocalDateTime currentDateTime; 
    private static String TDformat;
	public static ExtentHtmlReporter html;
	public static ExtentReports extent;
	public ExtentTest test, suiteTest;
	public String testCaseName, testNodes, testDescription, category, authors;
	public static File pathCheck;

	 //Get Date and Time
  	public static String GetCurrentDateAndTime()
  	{
  		try
  		{
  			currentDateTime = LocalDateTime.now();
  			TDformat = currentDateTime.format(DateTimeFormatter.ofPattern("d-M-yyyy_hh:mm:ss_a"));
  		}
  		catch(Exception ex)
  		{
  			System.out.println("Error in fetching Current Date and Time : " + ex.getMessage());
  		}
  	return TDformat;
  	}
  	
	//ExtentReport File storing Path
    public static String ExtentReportFileSavingPath()
    {
    	 currentDirectory = System.getProperty("user.dir");
    	 reportFileSavePath = currentDirectory + "\\ExtentReports\\";
    	 System.out.println(reportFileSavePath);
    	 PathCheck();
         return reportFileSavePath;
    }
 
    //Path Check Exists Verification
    public static void PathCheck()
    {
    	pathCheck = new File(reportFileSavePath.toString());
 		try {
 			if (pathCheck.exists()) 
 			{
 				File filesList[] = pathCheck.listFiles();
 				for (File file : filesList) 
 				{
 					if (file.isFile()) 
 					{
 						file.delete();
 					}
 					System.out.println("Deleted");
 				}	
 			 pathCheck.mkdir();
 			} 
 			else
 			{
 				pathCheck.mkdir();	
 			}
 		} catch (Exception ex) {
 			System.out.println("Deleting Files Error :" + ex.getMessage());
 			
 		}
    }
    
	//Use @BeforeSuite ==> startResult();
    //Report Config Setup
	public void startResult() {
		ExtentReportFileSavingPath();
		html = new ExtentHtmlReporter(reportFileSavePath+"report.html");
		html.config().setChartVisibilityOnOpen(false);
		html.config().setDocumentTitle("Automation Test Report");
		html.setAppendExisting(true);		
		extent = new ExtentReports();	
		extent.attachReporter(html);	
	}

	//Use @BeforeClass ==> startTestModule(testCaseName, testDescription);	
	public ExtentTest startTestModule(String testCaseName, String testDescription) {
		suiteTest = extent.createTest(testCaseName, testDescription);
		return suiteTest;
	}

	public ExtentTest startTestCase(String testNodes) {
		test = 	suiteTest.createNode(testNodes);
		return test;
	}

	//public abstract long takeSnap();
	public long takeSnap(WebDriver driver) {
		ExtentReportFileSavingPath();
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			 FileHandler.copy(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE),
					new File(reportFileSavePath+"/images/"+ number + TDformat+".jpg"));
		} catch (WebDriverException e) {

		} catch (IOException e) {

		}
		return number;
	}
	
	public void reportLogStep(WebDriver driver, String desc, String status, boolean bSnap)  {

		MediaEntityModelProvider img = null;
		if(bSnap && !status.equalsIgnoreCase("INFO"))
		{
			
			long snapNumber = 100000L;
			snapNumber = takeSnap(driver);
			try {
				img = MediaEntityBuilder.createScreenCaptureFromPath
						(reportFileSavePath+"/images/"+snapNumber+".jpg").build();
			} catch (IOException ex) {
				System.out.println("Error in Taking Screenshot : " + ex.getMessage());
			}
		}
		
		if(status.equalsIgnoreCase("PASS")) {
			test.pass(desc, img);			
		}else if (status.equalsIgnoreCase("FAIL")) {
			test.fail(desc, img);
			test.log(Status.FAIL, "Usage: BOLD TEXT");
			throw new RuntimeException();
		}else if (status.equalsIgnoreCase("WARNING")) {
			test.warning(desc, img);
		}else if (status.equalsIgnoreCase("INFO")) {
			test.info(desc);
		}							
	}


	public void reportStep(WebDriver driver, String desc, String status) {
		reportLogStep(driver, desc, status, true);
	}

	//Use @AfterSuite
	public void endResult() {
		extent.flush();
	}	
	
}

package sel.reports.Java_SeleniumExtentReports;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleTestcase {

	private static WebDriver driver;
	private static ExtentTest test;
	public static String Status;
	public static String testCaseName = "Google Title Verification";
	public static String testDescription = "Verify Google title once Google Homepage is loaded successfully";
	public static String testNodes = "Testcase 1 - Title Validation";
	public static String category = "Regression";
	public static String authors = "Automation Crackers - Rajesh";
	public static String browserName = "chrome";

	public static void main(String[] args) {

		WebDriverListener wl = new WebDriverListener();
		wl.startResult();
		wl.startTestModule(testCaseName, testDescription);
		test = wl.startTestCase(testNodes);
		test.assignCategory(category);
		test.assignAuthor(authors);

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		driver.get("https://www.google.com/");
		String title = driver.getTitle().trim();
		System.out.println(driver.getTitle());

		String tit = "Google";
		if (title.equalsIgnoreCase(tit)) {
			Status = "Pass";

		} else {
			Status = "Fail";
		}
		System.out.println(Status);
		wl.reportStep(driver, "Title Verification", Status);
		driver.quit();
		wl.endResult();
	}

}

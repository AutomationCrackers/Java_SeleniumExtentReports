package sel.reports.Java_SeleniumExtentReports;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverListener extends ExtentReportHelper
{

	public int i = 1;
    WebDriverWait wait;  
  	
	public void beforeAlertAccept(WebDriver driver) {
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void afterAlertAccept(WebDriver driver) {
		reportStep(driver,"The alert is accepted", "pass");

	}

	public void afterAlertDismiss(WebDriver driver) {
		reportStep(driver,"The alert is dismissed", "pass");

	}

	public void beforeAlertDismiss(WebDriver driver) {
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		reportStep(driver,"The browser loaded the URL " + url, "pass");

	}

	public void beforeNavigateBack(WebDriver driver) {
		
	}

	public void afterNavigateBack(WebDriver driver) {
		reportStep(driver,"The browser has loaded the previous page from the history", "pass");		
	}

	public void beforeNavigateForward(WebDriver driver) {

	}

	public void afterNavigateForward(WebDriver driver) {
		reportStep(driver,"The browser has loaded the next page from the history", "pass");


	}

	public void beforeNavigateRefresh(WebDriver driver) {

	}

	public void afterNavigateRefresh(WebDriver driver) {
		reportStep(driver,"The browser has reloaded successfully", "pass");
		takeSnap(driver);
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		reportStep(driver,"The element " + element + " is clicked successfully", "pass");
		takeSnap(driver);
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		reportStep(driver,"The value " + keysToSend[0] + " is entered successfully in element " + element, "pass");
	}

	public void beforeScript(String script, WebDriver driver) {

	}

	public void afterScript(String script, WebDriver driver) {

	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {

	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		reportStep(driver,"The driver is moved to the window with title " + driver.getTitle(), "pass");
	}

	public void onException(Throwable throwable, WebDriver driver) {

		if (throwable instanceof NoSuchFrameException) {
			reportStep(driver,"No frame found\n" + throwable.getMessage(), "fail");
			throw new RuntimeException();
		}
		if (throwable instanceof NoSuchWindowException) {
			reportStep(driver,"No frame found\n" + throwable.getMessage(), "fail");
			throw new RuntimeException();
		}
		if (throwable instanceof NoSuchSessionException) {
			reportStep(driver,"NoSuchSessionException" + throwable.getMessage(), "fail");
			throw new RuntimeException();
		} 
		else if (throwable instanceof NullPointerException) {
			reportStep(driver,"NullPointerException" + throwable.getMessage(), "fail");
			throw new RuntimeException();
		}
		else if (throwable instanceof NoSuchElementException) {
			reportStep(driver,"NoSuchElementException\n" + throwable.getMessage(), "fail");
			throw new RuntimeException();
		} 
		else if (throwable instanceof NoAlertPresentException) {
			reportStep(driver,"NoAlertPresentException", "fail");
		}
	}


	

	public void beforeGetText(WebElement element, WebDriver driver) {
		
		
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) {
		
		
	}
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
	}
}

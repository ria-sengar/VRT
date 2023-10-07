package sel.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class Regression {
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	@BeforeClass
	public void beforeClass() {
		
		report = new ExtentReports(System.getProperty("user.dir") + "/report/Report.html");
		
		try {
	    FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/Images/diffImages/"));
	    FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/Images/screenshots/"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.setProperty("webdriver.gecko.driver", "/Users/riasengar/Downloads/geckodriver");
	    driver = new FirefoxDriver();
	    driver.get("https://demo.guru99.com/test/newtours/");
	    driver.findElement(By.name("userName")).sendKeys("tutorial");
	    driver.findElement(By.name("password")).sendKeys("tutorial");
	    driver.findElement(By.xpath("//input[@value='Submit']")).click();
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		test =  report.startTest(method.getName());
	}
	
	public BufferedImage readImage(String imagePath) {
	    try {
	        File imageFile = new File(imagePath);
	        return ImageIO.read(imageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception appropriately, e.g., log the error or throw a custom exception.
	        return null;
	    }
	}
	
	@DataProvider(name="urls")
	public static Object[][] urls(){
		
		return new Object[][] {
			{"https://demo.guru99.com/test/newtours/index.php","Homepage"},
			{"https://demo.guru99.com/test/newtours/reservation.php","ReservationPage"},
			{"https://demo.guru99.com/test/newtours/reservation2.php","ReservationPage2"},
		};
	}
	
	@Test(dataProvider = "urls")
	public void regression(String url, String name, Method method, ITestContext context) {
		context.getCurrentXmlTest().addParameter("image", name);
		test = report.startTest(method.getName()+ "||"+ url);
		driver.get(url);
	    new ScreenCaptureUtility().takePageScreenshot(driver, name);
	    Assert.assertTrue(new ScreenCaptureUtility().areImagesEqual(name, name));
		
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult result, ITestContext context) {
	    String image = context.getCurrentXmlTest().getParameter("image");

	    if (result.getStatus() == ITestResult.SUCCESS) {
	        test.log(LogStatus.PASS, "Test Passed");
	    } else if (result.getStatus() == ITestResult.FAILURE) {
	        // Create an absolute path for the difference image
	        String absoluteDiffImagePath = System.getProperty("user.dir") + "/Images/diffImages/" + image + ".png";
	        
	        // Log the failure status along with the absolute difference image path
	        String diff = test.addScreenCapture(absoluteDiffImagePath);
	        test.log(LogStatus.FAIL, "Test Failed", "Screenshot Difference: \n" + diff);
	        test.log(LogStatus.FAIL, result.getThrowable());
	    } else if (result.getStatus() == ITestResult.SKIP) {
	        test.log(LogStatus.SKIP, "Test Skipped");
	    }
	}
	

	
	@AfterClass
	public void afterclass() {
		driver.quit();
		report.endTest(test);
		report.flush();
	}

}

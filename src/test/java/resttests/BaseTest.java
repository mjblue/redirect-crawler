package resttests;


import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utils.extentreports.ExtentManager;

public class BaseTest {
	
	
	protected static ExtentReports extent;
   protected static ThreadLocal parentTest = new ThreadLocal();
   protected static ThreadLocal test = new ThreadLocal();

	@BeforeSuite
	public void beforeSuite() {
		extent = ExtentManager.createInstance("extent.html");
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
		extent.attachReporter(htmlReporter);
	}
	
   @BeforeClass
   public synchronized void beforeClass() {
       ExtentTest parent = extent.createTest(getClass().getName());
       parentTest.set(parent);
   }

   @BeforeMethod
   public synchronized void beforeMethod(Method method) {
       ExtentTest child = ((ExtentTest) parentTest.get()).createNode(method.getName());
       test.set(child);
   }

   @AfterMethod
   public synchronized void afterMethod(ITestResult result) {
       if (result.getStatus() == ITestResult.FAILURE)
           ((ExtentTest) test.get()).fail(result.getThrowable());
       else if (result.getStatus() == ITestResult.SKIP)
           ((ExtentTest) test.get()).skip(result.getThrowable());
       else
           ((ExtentTest) test.get()).pass("Test passed");

       extent.flush();
   }

	@AfterClass(description = "Class Level Teardown!")
	public void teardown () {

	}

}

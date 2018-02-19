package utils.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Attachment;
import resttests.BaseTest;
import utils.excelutils.ExcelUtil;
import utils.extentreports.ExtentManager;
import utils.extentreports.ExtentTestManager;


public class TestListener extends BaseTest implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

 
    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog (String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in onStart method " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " +  getTestMethodName(iTestResult) + " start");
        //Start operation for extentreports.
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(),"");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
        //Extentreports log operation for passed tests.
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        //Update the test result on excel sheet
        try
		{
			ExcelUtil.setCellData("PASSED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("I am in onTestFailure method " +  getTestMethodName(iTestResult) + " failed");

        //Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");

 
        //Extentreports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed");

        //Update the test result on excel sheet
        try
		{
			ExcelUtil.setCellData("FAILED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped");
        //Extentreports log operation for skipped tests.
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");

        //Update the test result on excel sheet
        try
		{
			ExcelUtil.setCellData("SKIPPED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));

        //Update the test result on excel sheet
        //ExcelUtil.setCellData("FAILED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
    }

}

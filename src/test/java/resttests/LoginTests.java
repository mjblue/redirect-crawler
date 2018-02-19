package resttests;

import static io.restassured.RestAssured.when;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.clarks.autotest.utils.Config;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import utils.excelutils.ExcelUtil;
import utils.extentreports.ExtentTestManager;
import utils.listeners.TestListener;



//In order to eliminate attachment problem for Allure, you should add @Listener line.
//link: https://github.com/allure-framework/allure1/issues/730
@Listeners({ TestListener.class })
@Epic("Regression Tests")
@Feature("Login Tests")
public class LoginTests extends BaseTest {

	@DataProvider
	public Object[][] redirectList() throws Exception
	{

		Object[][] testObjArray = ExcelUtil.getTableArray("testdata.xlsx", "urls");

		return (testObjArray);

	}

	@BeforeTest
	public void setupTestData () {
		//Set Test Data Excel and Sheet
		System.out.println("************Setup Test Level Data**********");
	}

	@Test(dataProvider = "redirectList", priority = 0, description = "Invalid Login Scenario with wrong username and password.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Login test with wrong username and wrong password.")
	@Story("Invalid username and password login test")
	public void redirectURLs(String url, String responseCode, String redirect) throws InterruptedException, IOException
	{
		//extentreports Description
		ExtentTestManager.getTest().setDescription("Invalid Login Scenario with wrong username and password.");

		//Login to N11 with first row of the login data

		String sitehome = Config.getLocalisedMandatoryPropValue("url.frontend");

		String location = when().post(sitehome+url).then().statusCode(Integer.parseInt(responseCode)).extract().header("Location");

		Assert.assertTrue(location.contains(redirect));
		//Set test row number to 1
		//ExcelUtil.setRowNumber(1);

		//Set Test Status Column number to 5
		//ExcelUtil.setColumnNumber(5);

		//*************ASSERTIONS***********************
		Thread.sleep(500);
		//Verify password message by using excel's 2st row, 5th column
		//Row and Column numbers starting from 0. Thus we need to write 1 and 4, instead of 2 and 5!
	}

}

package resttests;

import static io.restassured.RestAssured.when;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import utils.excelutils.ExcelUtil;


// In order to eliminate attachment problem for Allure, you should add @Listener line.
// link: https://github.com/allure-framework/allure1/issues/730
// @Listeners({ TestListener.class })

public class LoginTest extends BaseTest
{

	@DataProvider
	public Object[][] redirectList() throws Exception
	{

		String input = System.getProperty("xls");

		if (StringUtils.isEmpty(input))
		{
			input = "testdata.xlsx";
		}

		Object[][] testObjArray = ExcelUtil.getTableArray(input, "urls");

		return (testObjArray);

	}

	@BeforeTest
	public void setupTestData()
	{
		//Set Test Data Excel and Sheet
		System.out.println("************Setup Test Level Data**********");
	}

	@Test(dataProvider = "redirectList", priority = 0, description = "Check Redirect URLs.")
	public void redirectURLs(String baseUrl, String url, String responseCode, String redirect)
			throws InterruptedException, IOException
	{

		((ExtentTest) test.get()).getModel().setDescription("Testing URL redirect:"+url);
		((ExtentTest) test.get()).getModel().setName("Testing URL redirect:"+url);
		((ExtentTest) test.get()).info(url);
		if (StringUtils.isNotBlank(baseUrl))
			
		{
			String location = when().post(baseUrl + url).then().statusCode(Integer.parseInt(responseCode)).extract()
					.header("Location");
			Assert.assertTrue(location.contains(redirect));
		} else {
			((ExtentTest) test.get()).skip("empty row");
			throw new SkipException("empty row");		
		}


	}

}


package utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

        /* OB: extentTestMap holds the information of thread ids and ExtentTest instances.
                extentreports instance created by calling getReporter() method from ExtentManager.
                At startTest() method, an instance of ExtentTest created and put into extentTestMap with current thread id.
                At endTest() method, test ends and ExtentTest instance got from extentTestMap via current thread id.
                At getTest() method, return ExtentTest instance in extentTestMap by using current thread id.
         */

public class ExtentManager {
	private static ExtentReports extent;
   
   public static ExtentReports getInstance() {
   	if (extent == null)
   		createInstance("test-output/extent.html");
   	
       return extent;
   }
   
   public static ExtentReports createInstance(String fileName) {
       ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
       htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
       htmlReporter.config().setChartVisibilityOnOpen(true);
       htmlReporter.config().setTheme(Theme.STANDARD);
       htmlReporter.config().setDocumentTitle(fileName);
       htmlReporter.config().setEncoding("utf-8");
       htmlReporter.config().setReportName(fileName);
       
       extent = new ExtentReports();
       extent.attachReporter(htmlReporter);
       
       return extent;
   }
}
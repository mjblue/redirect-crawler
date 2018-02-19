package resttests;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
	

	@BeforeClass (description = "Class Level Setup!")
	public void setup () {

	}

	@AfterClass(description = "Class Level Teardown!")
	public void teardown () {

	}

}

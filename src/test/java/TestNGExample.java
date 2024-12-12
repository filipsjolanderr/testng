import org.testng.Assert;
import org.testng.annotations.*;

public class TestNGExample {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - Runs once before the entire test suite.");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - Runs before each test.");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - Runs once before the class.");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - Runs before each test method.");
    }

    @Test(groups = {"smoke"})
    public void testMethod1() {
        System.out.println("Running Test Method 1");
        Assert.assertTrue(true);
    }

    @Test(groups = {"regression"})
    public void testMethod2() {
        System.out.println("Running Test Method 2");
        Assert.assertTrue(true);
    }

    @Test(dependsOnMethods = {"testMethod2"})
    public void testMethod3() {
        System.out.println("Running Test Method 3 (depends on testMethod2)");
        Assert.assertTrue(true);
    }

    @Test(dataProvider = "dataProviderExample")
    public void testMethod4(String input, int number) {
        System.out.println("Running Test Method 4 with data: " + input + ", " + number);
        Assert.assertTrue(number > 0);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - Runs after each test method.");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class - Runs once after the class.");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - Runs after each test.");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - Runs once after the entire test suite.");
    }

    @DataProvider
    public Object[][] dataProviderExample() {
        return new Object[][] {
                {"Test Case 1", 10},
                {"Test Case 2", 20}
        };
    }
}

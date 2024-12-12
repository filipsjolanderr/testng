import org.testng.Assert;
import org.testng.annotations.*;

public class TestNGExample {

    private Calculator calculator;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - Setting up global test resources...");
        // Imagine this as setting up a database connection pool or reading global config
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - Initializing test-specific resources...");
        // For example, we could reset a test environment here
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - Instantiating Calculator instance.");
        calculator = new Calculator();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - Making sure calculator is in pristine state.");
        // If there was a reset method, we could call it here.
    }

    @Test(groups = {"smoke"})
    public void testAddition() {
        System.out.println("Running Test Method: testAddition (smoke)");
        int result = calculator.add(3, 4);
        Assert.assertEquals(result, 7, "Addition result should be 7");
    }

    @Test(groups = {"regression"})
    public void testSubtraction() {
        System.out.println("Running Test Method: testSubtraction (regression)");
        int result = calculator.subtract(10, 5);
        Assert.assertEquals(result, 5, "Subtraction result should be 5");
    }

    @Test(dependsOnMethods = {"testSubtraction"})
    public void testMultiplication() {
        System.out.println("Running Test Method: testMultiplication (depends on testSubtraction)");
        int result = calculator.multiply(6, 7);
        Assert.assertEquals(result, 42, "Multiplication result should be 42");
    }

    @Test(dataProvider = "dataProviderExample")
    public void testDivision(String scenario, int a, int b, int expected) {
        System.out.println("Running Test Method: testDivision with data: " + scenario + " (" + a + " / " + b + ")");
        if (b == 0) {
            // Expect an exception
            try {
                calculator.divide(a, b);
                Assert.fail("Expected IllegalArgumentException for division by zero");
            } catch (IllegalArgumentException e) {
                Assert.assertTrue(e.getMessage().contains("Division by zero"));
            }
        } else {
            int result = calculator.divide(a, b);
            Assert.assertEquals(result, expected, "Division result should match expected value");
        }
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - Test method completed. Performing cleanup if needed.");
        // If there was a teardown step, such as removing temporary files, it would go here.
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class - Releasing Calculator instance resources.");
        calculator = null;  // Simulate cleanup
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - Cleaning up test-specific resources.");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - Global cleanup operations (e.g., closing DB connections).");
    }

    @DataProvider
    public Object[][] dataProviderExample() {
        return new Object[][] {
                {"Simple Division", 20, 5, 4},
                {"Division by zero scenario", 10, 0, -1},  // expected value ignored because it should fail before compare
                {"Negative Division", -20, 4, -5}
        };
    }
}

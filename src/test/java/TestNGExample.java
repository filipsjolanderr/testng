
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;


public class TestNGExample {

    private Calculator calculator;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - Setting up global test resources...");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - Initializing test-specific resources...");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - Instantiating Calculator instance.");
        calculator = new Calculator();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - Ensuring calculator state is clean.");
    }

    @Test(groups = {"smoke"}, priority = 1)
    public void testAddition() {
        System.out.println("Running testAddition (smoke)");
        int result = calculator.add(3, 4);
        Assert.assertEquals(result, 7, "Addition result should be 7");
    }

    @Test(groups = {"regression"}, priority = 2)
    public void testSubtraction() {
        System.out.println("Running testSubtraction (regression)");
        int result = calculator.subtract(10, 5);
        Assert.assertEquals(result, 5, "Subtraction result should be 5");
    }

    @Test(dependsOnMethods = {"testSubtraction"}, priority = 3)
    public void testMultiplication() {
        System.out.println("Running testMultiplication (depends on testSubtraction)");
        int result = calculator.multiply(6, 7);
        Assert.assertEquals(result, 42, "Multiplication result should be 42");
    }

    @Test(dataProvider = "dataProviderExample", priority = 4)
    public void testDivision(String scenario, int a, int b, int expected) {
        System.out.println("Running testDivision with data: " + scenario + " (" + a + " / " + b + ")");
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

    // Test a method that should throw an exception
    @Test(expectedExceptions = IllegalArgumentException.class, priority = 7)
    public void testDivisionByZeroException() {
        System.out.println("Running testDivisionByZeroException");
        calculator.divide(10, 0);
    }

    // Invocation count & parallel test execution
    @Test(invocationCount = 3, threadPoolSize = 3, priority = 8)
    public void testMultipleInvocations() {
        System.out.println("Running testMultipleInvocations in parallel");
        // Just a simple test to ensure parallel runs. Could perform thread-safe operations.
        int result = calculator.add(1, 1);
        Assert.assertEquals(result, 2);
    }

    // Conditionally skip a test
    @Test(priority = 9)
    public void testConditionalSkip() {
        System.out.println("Running testConditionalSkip");
        boolean condition = false; // For demonstration, let's skip if false
        if (!condition) {
            throw new SkipException("Skipping this test because condition is not met.");
        }
        // If condition were true, we could proceed with assertions.
        Assert.assertTrue(true);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - Test method completed.");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class - Releasing Calculator instance resources.");
        calculator = null;
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - Cleaning up test-specific resources.");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - Global cleanup operations.");
    }

    @DataProvider
    public Object[][] dataProviderExample() {
        return new Object[][] {
                {"Simple Division", 20, 5, 4},
                {"Division by zero scenario", 10, 0, -1},
                {"Negative Division", -20, 4, -5}
        };
    }
}

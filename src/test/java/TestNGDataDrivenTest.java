import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNGDataDrivenTest {

    @Test(dataProvider = "dataProviderExample")
    public void testDataDriven(String input, int number) {
        System.out.println("Running Data-Driven Test with: " + input + ", " + number);
        Assert.assertTrue(number > 0);
    }

    @DataProvider
    public Object[][] dataProviderExample() {
        return new Object[][] {
                {"Test Case A", 1},
                {"Test Case B", 2},
                {"Test Case C", 3}
        };
    }
}

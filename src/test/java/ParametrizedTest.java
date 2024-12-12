
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Each instance represents a test scenario with its own parameters.
 */
public class ParametrizedTest {
    private final int a;
    private final int expectedResult;

    public ParametrizedTest(int a, int expectedResult) {
        this.a = a;
        this.expectedResult = expectedResult;
    }

    @Test
    public void testSquare() {
        System.out.println("Running ParametrizedTest with input: " + a);
        int result = a * a;
        Assert.assertEquals(result, expectedResult, "Square of " + a + " should be " + expectedResult);
    }
}

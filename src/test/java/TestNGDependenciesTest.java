import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNGDependenciesTest {

    @Test(groups = "smoke")
    public void testSmoke() {
        System.out.println("Running Smoke Test");
        Assert.assertTrue(true);
    }

    @Test(groups = "regression", dependsOnGroups = "smoke")
    public void testRegression() {
        System.out.println("Running Regression Test");
        Assert.assertTrue(true);
    }
}

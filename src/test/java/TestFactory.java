
import org.testng.annotations.Factory;

public class TestFactory {
    @Factory
    public Object[] createInstances() {
        return new Object[] {
                new ParametrizedTest(2, 4),
                new ParametrizedTest(3, 9),
                new ParametrizedTest(4, 16)
        };
    }
}

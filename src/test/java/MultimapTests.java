import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.util.List;
import java.util.Set;

public class MultimapTests {

    private ListMultimap<String, Integer> multiMap;
    private ListMultimap<String, Integer> separateMultiMap;

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
        System.out.println("Before Class - Building a ListMultimap instance.");
        multiMap = MultimapBuilder.treeKeys().arrayListValues().build();
        separateMultiMap = MultimapBuilder.treeKeys().arrayListValues().build();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - Clearing and reinitializing the multimap.");
        multiMap.clear();
    }

    @Test(groups = {"smoke"}, priority = 1)
    public void testInsertionAndRetrieval() {
        System.out.println("Running testInsertionAndRetrieval (smoke)");
        multiMap.put("apple", 1);
        multiMap.put("apple", 2);
        multiMap.put("banana", 3);

        Assert.assertEquals(multiMap.get("apple").size(), 2, "Expected 2 values for key 'apple'");
        Assert.assertEquals(multiMap.get("banana").size(), 1, "Expected 1 value for key 'banana'");
        Assert.assertTrue(multiMap.get("apple").contains(2), "Expected value 2 to be associated with 'apple'");
    }

    @Test(groups = {"regression"}, priority = 2)
    public void testKeyOrdering() {
        System.out.println("Running testKeyOrdering (regression)");
        multiMap.put("zebra", 10);
        multiMap.put("apple", 1);
        multiMap.put("mango", 5);

        Set<String> keys = multiMap.keySet();
        // Since it's a tree-based key structure, keys should be sorted alphabetically: apple, mango, zebra
        Assert.assertEquals(keys.size(), 3);
        Assert.assertTrue(keys.toString().equals("[apple, mango, zebra]"),
                "Keys should be in alphabetical order: [apple, mango, zebra]");
    }

    @Test(dependsOnMethods = {"testKeyOrdering"}, priority = 3)
    public void testRemoval() {
        System.out.println("Running testRemoval (depends on testKeyOrdering)");
        multiMap.put("apple", 1);
        multiMap.put("apple", 2);

        // Remove a single value
        boolean removed = multiMap.remove("apple", 1);
        Assert.assertTrue(removed, "Expected to remove one value from 'apple' key");

        List<Integer> appleValues = multiMap.get("apple");
        Assert.assertEquals(appleValues.size(), 1, "Now only one value should remain associated with 'apple'");
        Assert.assertTrue(appleValues.contains(2), "Remaining value should be 2");
    }

    @Test()
    public void testRemoveAll() {
        System.out.println("Running testRemoveAll");

        multiMap.put("apple", 1);
        multiMap.put("apple", 2);
        multiMap.put("pear", 2);

        List<Integer> appleValues = multiMap.removeAll("apple");
        Assert.assertEquals(appleValues.size(), 2, "2 values associated with 'apple' should have been removed");
        Integer appleSum = appleValues.get(0) + appleValues.get(1);
        Assert.assertEquals(appleSum, 3, "The total sum of apples should be 3");
        Assert.assertEquals(multiMap.size(), 1, "One pear item should be left in the original map");
    }

    @Test(dataProvider = "dataProviderExample", priority = 4)
    public void testDataDrivenInsertion(String scenario, String key, int value, int expectedSize) {
        System.out.println("Running testDataDrivenInsertion with data: " + scenario + " (" + key + ", " + value + ")");
        separateMultiMap.put(key, value);
        Assert.assertEquals(separateMultiMap.get(key).size(), expectedSize,
                "Size of values for key '" + key + "' should match expected value");
    }

    // Test a method that should throw an exception - for example, test an immutable collection modification
    @Test(expectedExceptions = UnsupportedOperationException.class, priority = 7)
    public void testImmutableModificationException() {
        System.out.println("Running testImmutableModificationException");
        // Create an immutable list and try modifying it
        List<String> immutableList = com.google.common.collect.ImmutableList.of("one", "two", "three");
        immutableList.add("four"); // This should throw an UnsupportedOperationException
    }

    // Invocation count & parallel test execution
    @Test(invocationCount = 3, threadPoolSize = 3, priority = 8)
    public void testMultipleInvocations() {
        System.out.println("Running testMultipleInvocations in parallel");
        // Just a concurrency demonstration: insert concurrently and check size
        multiMap.put("parallel", 100);
        Assert.assertTrue(multiMap.get("parallel").contains(100));
    }

    // Conditionally skip a test
    @Test(priority = 9)
    public void testConditionalSkip() {
        System.out.println("Running testConditionalSkip");
        boolean condition = false; // For demonstration, let's skip if false
        if (!condition) {
            throw new SkipException("Skipping this test because condition is not met.");
        }
        Assert.assertTrue(true);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - Test method completed.");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class - Clearing the multimap instance resources.");
        multiMap = null;
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
                {"First insertion", "apple", 10, 1},
                {"Second insertion (same key)", "apple", 20, 2},
                {"Different key insertion", "banana", 5, 1}
        };
    }
}

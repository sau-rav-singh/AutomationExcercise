package utilities;

import org.testng.Assert;
import reporting.ExtentReportManager;

public class CustomAssertions {

    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            ExtentReportManager.logPassDetails("Assertion Passed: " + message);
        } catch (AssertionError e) {
            ExtentReportManager.logFailureDetails("Assertion Failed: " + message);
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            ExtentReportManager.logPassDetails("Assertion Passed: " + message);
        } catch (AssertionError e) {
            ExtentReportManager.logFailureDetails("Assertion Failed: " + message +
                    ". Expected: " + expected + ", but got: " + actual);
            throw e;
        }
    }
}

package utilities;

import listeners.DriverListner;
import org.testng.Assert;
import reporting.ExtentReportManager;
import static utilities.DriverManager.getDriver;

public class CustomAssertions {

    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            ExtentReportManager.logPassDetails("Assertion Passed: " + message);
        } catch (AssertionError e) {
            ExtentReportManager.logFailureDetails("Assertion Failed: " + message);
            DriverListner.embedScreenshot(getDriver());
            throw e;
        }
    }
}

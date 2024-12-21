package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reporting.ExtentReportManager;

import java.util.Arrays;
import java.util.Optional;

public class ExtentReportConfigListener implements ITestListener {

	private static ExtentReports extentReports = ExtentReportManager.getInstance();
	private static final ThreadLocal<ExtentTest> extentTest = ThreadLocal.withInitial(() -> null);

	public static Optional<ExtentTest> getExtentTest() {
		return Optional.ofNullable(extentTest.get());
	}

	@Override
	public void onStart(ITestContext context) {
		// Ensure the ExtentReports instance is initialized
		extentReports = ExtentReportManager.getInstance();
	}

	@Override
	public void onFinish(ITestContext context) {
		if (extentReports != null) {
			extentReports.flush();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReports.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		getExtentTest().ifPresent(test -> test.pass("Test passed: " + result.getMethod().getMethodName()));
		extentTest.remove();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		getExtentTest().ifPresent(test -> {
			test.fail("Test failed: " + result.getMethod().getMethodName());
			test.fail(result.getThrowable());

			// Add detailed stack trace as expandable logs
			String stackTrace = Arrays.toString(result.getThrowable().getStackTrace()).replaceAll(",", "<br>");
			String formattedTrace = "<details><summary>Click to view exception logs</summary>" + stackTrace + "</details>";
			test.fail(formattedTrace);
		});
		extentTest.remove();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getExtentTest().ifPresent(test -> test.skip("Test skipped: " + result.getMethod().getMethodName()));
		extentTest.remove();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// This method can be implemented if required
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}
}

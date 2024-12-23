package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import reporting.ExtentReportManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

import static listeners.ExtentReportConfigListener.getExtentTest;

public class DriverListner implements WebDriverListener {
    private final WebDriver driver;

    public DriverListner(WebDriver driver) {
        this.driver = driver;
    }

    public static String getBase64Screenshot(WebDriver driver) throws IOException {
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return Base64.getEncoder().encodeToString(org.apache.commons.io.FileUtils.readFileToByteArray(sourceFile));
    }

    public static void embedScreenshot(WebDriver driver) {
        try {
            String base64Image = getBase64Screenshot(driver);
            getExtentTest().get().log(Status.INFO, "Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
        } catch (IOException e) {
            getExtentTest().get().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void beforeGetTitle(WebDriver driver) {
        System.out.println("Getting the title");
    }

    @Override
    public void beforeClick(WebElement element) {
        System.out.println(element + " is going to be clicked");
        ExtentReportManager.logPassDetails("Clicking on element: ");
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        try {
            ExtentReportManager.logPassDetails("Entered text ");
            String base64Image = getBase64Screenshot(driver);
            getExtentTest().get().log(Status.INFO, "Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
        } catch (Exception e) {
            ExtentReportManager.logFailureDetails("Failed to enter text ");
        }
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println(element + " was clicked");
        embedScreenshot(driver);
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        takeScreenshot();
    }

    private void takeScreenshot() {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String fileName = "screenshot_" + "On Error" + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(screenshot, new File(fileName));
            System.out.println("Screenshot captured: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

}

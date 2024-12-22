package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import reporting.ExtentReportManager;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static listeners.ExtentReportConfigListener.getExtentTest;
import static utilities.DriverManager.getDriver;

public class ExtentActions extends TestBase {
    private final WebElement element;

    public ExtentActions(WebElement element) {
        this.element = element;
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

    public void click(String elementName) {
        try {
            LoggerHelper.info("Clicking on: " + elementName);
            ExtentReportManager.logPassDetails("Clicking on: " + elementName);
            element.click();
            embedScreenshot(getDriver());

        } catch (Exception e) {
            ExtentReportManager.logFailureDetails("Failed to click on: " + elementName);
            throw e;
        }
    }

    public void sendKeys(String text, String elementName) {
        try {
            element.sendKeys(text);
            ExtentReportManager.logPassDetails("Entered text '" + text + "' into: " + elementName);
        } catch (Exception e) {
            ExtentReportManager.logFailureDetails("Failed to enter text into: " + elementName);
            throw e;
        }
    }

    public String getText(String elementName) {
        String text = element.getText();
        ExtentReportManager.logInfoDetails("Retrieved text '" + text + "' from: " + elementName);
        return text;
    }

    public boolean isDisplayed(String elementName) {
        boolean result = element.isDisplayed();
        ExtentReportManager.logInfoDetails(elementName + " is displayed: " + result);
        return result;
    }

}

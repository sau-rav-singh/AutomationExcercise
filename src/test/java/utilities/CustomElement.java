package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import reporting.ExtentReportManager;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static listeners.ExtentReportConfigListener.extentTest;


public class CustomElement extends TestBase {
    private final WebElement element;

    public CustomElement(WebElement element) {
        this.element = element;
    }

    public static String getBase64Screenshot(WebDriver driver) throws IOException {
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return Base64.getEncoder().encodeToString(org.apache.commons.io.FileUtils.readFileToByteArray(sourceFile));
    }

    public void click(String elementName) {
        try {
            ExtentTest clickStep = extentTest.get().createNode("Clicking on: " + elementName);
            LoggerHelper.info("Clicking on: " + elementName);
            element.click();
            clickStep.pass("Clicked on: " + elementName);
            embedScreenshot(driver, clickStep);
        } catch (Exception e) {
            ExtentReportManager.logFailureDetails("Failed to click on: " + elementName);
            throw e;
        }
    }

    public static void embedScreenshot(WebDriver driver, ExtentTest step) {
        try {
            String base64Image = getBase64Screenshot(driver);
            step.log(Status.INFO, "Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
        } catch (IOException e) {
            step.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
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

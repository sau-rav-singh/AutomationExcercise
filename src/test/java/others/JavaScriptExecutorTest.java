package others;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class JavaScriptExecutorTest {
    static WebDriver driver;
    JavascriptExecutor js;
    Actions actions;
    private static void takeScreenshot() {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/test/resources/ss.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://letcode.in/test");
        js = (JavascriptExecutor) driver;
        actions=new Actions(driver);
    }

    @Test
    public void inputTest() throws InterruptedException {
        WebElement input = driver.findElement(By.xpath("//header[normalize-space()='Input']/parent::div/footer/a"));
        js.executeScript("arguments[0].click();", input);
        WebElement fullName = driver.findElement(By.id("fullName"));
        js.executeScript("document.getElementById('fullName').value='saurav';");
        String text = (String) js.executeScript("return arguments[0].value;", fullName);
        Assert.assertEquals(text, "saurav");
    }

    @Test
    public void clickTest() {
        js.executeScript("window.scrollBy(0,500)");
        WebElement radio = driver.findElement(By.xpath("//header[normalize-space()='Radio']/parent::div/footer/a"));
        js.executeScript("arguments[0].click();", radio);
        WebElement firstYes = driver.findElement(By.xpath("//*[@id=\"yes\"]"));
        js.executeScript("document.getElementById('yes').click();");
        Assert.assertTrue(firstYes.isSelected());
        js.executeScript("document.querySelector('[id=\"no\"]').click();");
        Assert.assertFalse(firstYes.isSelected());
        takeScreenshot();
    }

    public WebElement sectionElement(String name) {
        return driver.findElement(By.xpath("//header[normalize-space()='" + name + "']/parent::div/footer/a"));
    }

    public WebElement elementById(String id){
        return  driver.findElement(By.id(id));
    }
    public WebElement elementByxpath(String id){
        return  driver.findElement(By.xpath(id));
    }
    public List<WebElement> elementsByxpath(String xpath){
        return  driver.findElements(By.xpath(xpath));
    }
    @AfterTest
    public void tearDown() {
       driver.quit();
    }
}

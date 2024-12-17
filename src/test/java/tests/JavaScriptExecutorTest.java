package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class JavaScriptExecutorTest {
    WebDriver driver;
    JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://letcode.in/test");
        js = (JavascriptExecutor) driver;
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
    }

    @Test
    public void inputTest() {
        WebElement input = driver.findElement(By.xpath("//header[normalize-space()='Input']/parent::div/footer/a"));
        js.executeScript("arguments[0].click();", input);
        WebElement fullName = driver.findElement(By.id("fullName"));
        js.executeScript("document.getElementById('fullName').value='saurav';");
        String text = (String) js.executeScript("return arguments[0].value;", fullName);
        Assert.assertEquals(text, "saurav");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}

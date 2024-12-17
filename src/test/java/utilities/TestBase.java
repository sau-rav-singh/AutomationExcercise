package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.PageObjectManager;

import java.time.Duration;

public class TestBase {
    public static WebDriver driver;
    public JavascriptExecutor js;
    protected PageObjectManager pageObjectManager;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        pageObjectManager = new PageObjectManager(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://automationexercise.com/");
        js = (JavascriptExecutor) driver;
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}

package utilities;

import listeners.DriverListner;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.annotations.*;
import pages.PageObjectManager;
import reporting.ExtentReportManager;

import java.time.Duration;

public class TestBase {
    protected PageObjectManager pageObjectManager;
    protected JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        DriverManager.setDriver(initializeDriver());
        WebDriver driver = DriverManager.getDriver();
        WebDriverListener myListner = new DriverListner(driver);
        driver=new EventFiringDecorator<>(myListner).decorate(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String url = ConfigManager.getProperty("app.url");
        driver.get(url);
        pageObjectManager = new PageObjectManager(driver);
        js = (JavascriptExecutor) driver;
        ExtentReportManager.getInstance(); // Initialize reporting
    }

    @AfterTest
    public void tearDown() {
        DriverManager.quitDriver();
    }

    private WebDriver initializeDriver() {
        String browser = ConfigManager.getProperty("browser").toLowerCase();
        return switch (browser) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }
}

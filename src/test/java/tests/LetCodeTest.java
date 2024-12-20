package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetCodeTest extends JavaScriptExecutorTest {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        String downloadPath = Paths.get(System.getProperty("user.dir"), "downloads").toString();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false); // Disable download prompt
        prefs.put("download.directory_upgrade", true); // Allow directory changes
        prefs.put("safebrowsing.enabled", true); // Enable safe browsing
        options.setExperimentalOption("prefs", prefs);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://letcode.in/file");
        driver.findElement(By.xpath("/html/body/app-root/app-filemanagement/section[1]/div/div/div[1]/div/div[2]/div/label/button[2]")).click();
        driver.get("https://demo.guru99.com/test/upload/");
        driver.findElement(By.id("uploadfile_0")).sendKeys("C:\\Users\\Singh\\Downloads\\sample.pdf");
        driver.findElement(By.id("terms")).click();
        driver.findElement(By.id("submitbutton")).click();

        driver.get("https://the-internet.herokuapp.com/upload");
        File uploadFile = new File("src/test/resources/ss.png");
        WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
        fileInput.sendKeys(uploadFile.getAbsolutePath());
        driver.findElement(By.id("file-submit")).click();
        WebElement fileName = driver.findElement(By.id("uploaded-files"));
        Assert.assertEquals(fileName.getText(), "ss.png");

    }

    @Test
    public void calenderTest() {
        sectionElement("Calendar").click();
        String month = driver.findElement(By.xpath("//div[@class='datepicker-nav-month']")).getText();
        String year = driver.findElement(By.xpath("//div[@class='datepicker-nav-year']")).getText();
        while (!(month.equals("April") && year.equals("2023"))) {
            driver.findElement(By.xpath("//div[@class='datepicker-nav']/button")).click();
            month = driver.findElement(By.xpath("//div[@class='datepicker-nav-month']")).getText();
            year = driver.findElement(By.xpath("//div[@class='datepicker-nav-year']")).getText();
        }
        driver.findElement(By.xpath("//div[contains(@class,'is-current-month')]//button[text()='26']")).click();
        String confirmationMessage = driver.findElement(By.xpath("//div[@class='content has-text-centered']/p")).getText();
        Assert.assertTrue(confirmationMessage.contains("4/26/23"));
    }

    @Test
    public void calenderTest2() {
        sectionElement("Calendar").click();
        driver.findElement(By.xpath("(//*[@class='datetimepicker-dummy-wrapper'])[2]/input[1]")).click();
        driver.findElement(By.xpath("(//*[@class='date-item is-today'])[2]")).click();
        driver.findElement(By.xpath("//*[@class='date-item is-today is-active']/parent::div/following-sibling::div[3]/button")).click();
    }

    @Test
    public void tableTest() {
        driver.get("https://letcode.in/advancedtable");
        Select dataSize = new Select(elementByxpath("//select"));
        dataSize.selectByValue("25");
        WebElement table = driver.findElement(By.id("advancedtable"));
        List<WebElement> rows;
        Map<String, Map<String, String>> finalData = new HashMap<>();
        WebElement nextButton = driver.findElement(By.xpath("//a[text()='Next']"));
        boolean flag = false;
        while (!flag) {
            rows = table.findElements(By.tagName("tr")).subList(1, table.findElements(By.tagName("tr")).size());
            for (WebElement row : rows) {
                Map<String, String> rowData = new HashMap<>();
                String sno = row.findElements(By.tagName("td")).getFirst().getText();
                List<WebElement> cells = row.findElements(By.tagName("td"));
                List<WebElement> headers = table.findElement(By.tagName("tr")).findElements(By.tagName("th"));
                for (int j = 0; j < cells.size(); j++) {
                    rowData.put(headers.get(j).getText(), cells.get(j).getText());
                }
                finalData.put(sno, rowData);
            }
            nextButton.click();
            try {
                Thread.sleep(2000);
                table = driver.findElement(By.id("advancedtable"));  // Refetch table element after navigation
                nextButton = driver.findElement(By.xpath("//a[text()='Next']"));
                String text = elementByxpath("//div[@id='advancedtable_info']").getText();
                String[] split = text.split("of");
                String totalData = split[1].trim().split(" ")[0].trim();
                flag = split[0].contains(totalData);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(finalData);
    }

    @Test
    public void inputTest() {
        sectionElement("Input").click();
        String fullName = "Sachin Tendulkar";
        WebElement name = elementById("fullName");
        name.sendKeys(fullName);
        Assert.assertEquals(elementById("fullName").getDomProperty("value"), fullName);
        elementById("join").sendKeys("!!!" + Keys.TAB);
        Assert.assertEquals(elementById("getMe").getDomProperty("value"), "ortonikc");
        elementById("clearMe").click();
        Assert.assertFalse(elementById("noEdit").isEnabled());
        WebElement readonlyInput = elementById("dontwrite");
        String readonlyAttribute = readonlyInput.getDomAttribute("readonly");
        Assert.assertEquals(readonlyAttribute, "true", "Readonly attribute is false");
    }

    @Test
    public void buttonTest() throws InterruptedException {
        sectionElement("Button").click();
        Point point = elementById("position").getLocation();
        System.out.println("X :" + point.getX());
        System.out.println("Y :" + point.getY());
        System.out.println(elementById("color").getCssValue("background-color"));
        Rectangle rectangle = elementById("property").getRect();
        System.out.println(rectangle.getDimension());
        System.out.println(rectangle.getWidth() + " " + rectangle.getHeight());
        Assert.assertFalse(elementById("isDisabled").isEnabled());
        WebElement elementToClickAndHold = driver.findElement(By.xpath("//*[text()='Click and Hold Button']/parent::div //button"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(elementToClickAndHold).perform();
        Thread.sleep(2000);
        actions.release().perform();
        String text = driver.findElement(By.xpath("//*[text()='Click and Hold Button']/parent::div //button/div/h2")).getText();
        Assert.assertEquals(text, "Button has been long pressed");
    }

    @Test
    public void selectTest() {
        sectionElement("Select").click();
        Select select = new Select(elementById("fruits"));
        select.selectByValue("0");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Apple");
        select.selectByVisibleText("Mango");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Mango");

        Select superheroes = new Select(elementById("superheros"));
        Assert.assertTrue(superheroes.isMultiple());
        superheroes.selectByIndex(0);
        superheroes.selectByIndex(1);
        List<WebElement> selectedHeroes = superheroes.getAllSelectedOptions();
        for (WebElement superhere : selectedHeroes) {
            System.out.println(superhere.getText());
        }
        superheroes.deselectAll();
    }

    @Test
    public void alertTest() {
        sectionElement("Alert").click();
        elementById("accept").click();
        driver.switchTo().alert();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();

        elementById("prompt").click();
        driver.switchTo().alert().sendKeys("saurav");
        driver.switchTo().alert().accept();
        System.out.println(elementById("myName").getText());
        System.out.println(driver.findElement(By.xpath("//p[contains(text(),'Modern')]")).isDisplayed());
        elementById("modern").click();
        System.out.println(driver.findElement(By.xpath("//p[contains(text(),'Modern')]")).getText());
    }

    @Test
    public void shadowDomTest() {
        sectionElement("Shadow").click();
        SearchContext shadowRoot = elementById("open-shadow").getShadowRoot();
        shadowRoot.findElement(By.cssSelector("#fname")).sendKeys("sauravsingh");
        js.executeScript("document.querySelector(\"my-web-component\").myRoot.querySelector('#lname').value=\"saurav\"");
    }

    @Test
    public void formsTest() {
        sectionElement("Forms").click();
        elementById("Date").sendKeys("27041993");


    }
}

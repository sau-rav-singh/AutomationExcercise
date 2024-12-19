package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LetCodeTest extends JavaScriptExecutorTest {

    @Test
    public void calenderTest() {
        sectionElement("Calendar").click();

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
    public void selectTest(){
        sectionElement("Select").click();
        Select select=new Select(elementById("fruits"));
        select.selectByValue("0");
        Assert.assertEquals(select.getFirstSelectedOption().getText(),"Apple");
        select.selectByVisibleText("Mango");
        Assert.assertEquals(select.getFirstSelectedOption().getText(),"Mango");

        Select superheroes=new Select(elementById("superheros"));
        Assert.assertTrue(superheroes.isMultiple());
        superheroes.selectByIndex(0);
        superheroes.selectByIndex(1);
        List<WebElement> selectedHeroes = superheroes.getAllSelectedOptions();
        for(WebElement superhere:selectedHeroes){
            System.out.println(superhere.getText());
        }
        superheroes.deselectAll();
    }
}

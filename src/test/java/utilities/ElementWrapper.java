package utilities;

import org.openqa.selenium.WebElement;

public class ElementWrapper {

    public static ExtentActions wrap(WebElement element) {
        return new ExtentActions(element);
    }
}


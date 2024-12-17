package utilities;

import org.openqa.selenium.WebElement;

public class ElementWrapper {
    public static CustomElement wrap(WebElement element) {
        return new CustomElement(element);
    }
}


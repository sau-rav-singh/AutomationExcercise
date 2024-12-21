package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ElementWrapper;

public class HomePage {

    @FindBy(css = ".product-image-wrapper")
    private WebElement firstProduct;

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginElement;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isHomePageLoaded() {
        return firstProduct.isDisplayed();
    }

    public void signupLoginClick() {
        ElementWrapper.wrap(signupLoginElement).click("SignUp/Login Button");
    }
}

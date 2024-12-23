package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Page
public class HomePage {

    @FindBy(css = ".product-image-wrapper")
    private WebElement firstProduct;

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginElement;

    @FindBy(xpath = "//*[contains(text(),'Logged in as')]")
    private WebElement loggedInConfirmation;

    @FindBy(xpath = "//*[contains(text(),'Delete')]")
    private WebElement deleteAccount;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isHomePageLoaded() {
        return firstProduct.isDisplayed();
    }

    public void signupLoginClick() {
        signupLoginElement.click();
    }

    public boolean isLoggedin() {
        return loggedInConfirmation.isDisplayed();
    }

    public void deleteAccountClick() {
        deleteAccount.click();
    }
}

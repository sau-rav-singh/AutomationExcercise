package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Page
public class LoginSignupPage {

    @FindBy(css = "div[class='signup-form'] h2")
    private WebElement newUserSignup;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmailInput;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupNameInput;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    public LoginSignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isNewUserSignUpDisplayed() {
        return newUserSignup.isDisplayed();
    }

    public void fillSignup(String name, String email) {
        (signupNameInput).sendKeys(name);
        (signupEmailInput).sendKeys(email);
        (signupButton).click();
    }
}
package pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    private final WebDriver driver;
    private HomePage homePage;
    private LoginSignupPage loginSignupPage;
    private SignUpPage signUpPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage getHomePage() {
        return homePage == null ? homePage = new HomePage(driver) : homePage;
    }
    public LoginSignupPage getloginSignupPage() {
        return loginSignupPage == null ? loginSignupPage = new LoginSignupPage(driver) : loginSignupPage;
    }

    public SignUpPage getSignUpPage() {
        return signUpPage == null ? signUpPage = new SignUpPage(driver) : signUpPage;
    }

}
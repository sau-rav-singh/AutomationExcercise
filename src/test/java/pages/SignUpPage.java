package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Page
public class SignUpPage {

    @FindBy(xpath = "//div[@class='login-form']/h2/b")
    private WebElement formHeader;

    @FindBy(id = "id_gender1")
    private WebElement mrRadioButtonLocator;

    @FindBy(id = "password")
    private WebElement passwordLocator;

    @FindBy(id="first_name")
    private WebElement firstNameLocator;

    @FindBy(id="last_name")
    private WebElement lastNameLocator;

    @FindBy(id="address1")
    private WebElement addressLocator;

    @FindBy(id="state")
    private WebElement stateLocator;

    @FindBy(id="city")
    private WebElement cityLocator;

    @FindBy(id="zipcode")
    private WebElement zipcodeLocator;

    @FindBy(id="mobile_number")
    private WebElement mobile_numberLocator;

    @FindBy(xpath = "//button[@data-qa='create-account']")
    private WebElement createAccount;

    @FindBy(xpath = "//*[@data-qa='account-created']/b")
    private WebElement accountCreationConfirmation;

    @FindBy(xpath = "//*[@data-qa='continue-button']")
    private WebElement continueLocator;

    @FindBy(xpath = "//*[@data-qa='account-deleted']/b")
    private WebElement accountDeletedConfirmation;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isFormHeaderDisplayed() {
        return formHeader.isDisplayed();
    }

    public void selectMr() {
       (mrRadioButtonLocator).click();
    }

    public void enterPassword(String password) {
       (passwordLocator).sendKeys(password);
    }

    public void enterFirstName(String firstName) {
        (firstNameLocator).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        (lastNameLocator).sendKeys(lastName);
    }

    public void enterAddress(String address) {
        (addressLocator).sendKeys(address);
    }

    public void enterState(String state) {
        (stateLocator).sendKeys(state);
    }

    public void enterCity(String city) {
        (cityLocator).sendKeys(city);
    }

    public void enterZipcode(String zipcode) {
        (zipcodeLocator).sendKeys(zipcode);
    }

    public void enterMobileNumber(String mobileNumber) {
        (mobile_numberLocator).sendKeys(mobileNumber);
    }

    public void clickCreateAccount() {
        (createAccount).click();
    }

    public boolean accountCreationConfirmationIsDisplayed(){
        return accountCreationConfirmation.isDisplayed();
    }

    public void clickContinue(){
        (continueLocator).click();
    }
    public boolean accountDeletedConfirmationIsDisplayed(){
        return accountDeletedConfirmation.isDisplayed();
    }
}

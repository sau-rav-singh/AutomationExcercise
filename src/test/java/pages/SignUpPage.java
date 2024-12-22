package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ElementWrapper;
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
        ElementWrapper.wrap(mrRadioButtonLocator).click("mrRadioButtonLocator");
    }

    public void enterPassword(String password) {
        ElementWrapper.wrap(passwordLocator).sendKeys(password,"Password Field");
    }

    public void enterFirstName(String firstName) {
        ElementWrapper.wrap(firstNameLocator).sendKeys(firstName, "First Name Field");
    }

    public void enterLastName(String lastName) {
        ElementWrapper.wrap(lastNameLocator).sendKeys(lastName, "Last Name Field");
    }

    public void enterAddress(String address) {
        ElementWrapper.wrap(addressLocator).sendKeys(address, "Address Field");
    }

    public void enterState(String state) {
        ElementWrapper.wrap(stateLocator).sendKeys(state, "State Field");
    }

    public void enterCity(String city) {
        ElementWrapper.wrap(cityLocator).sendKeys(city, "City Field");
    }

    public void enterZipcode(String zipcode) {
        ElementWrapper.wrap(zipcodeLocator).sendKeys(zipcode, "Zipcode Field");
    }

    public void enterMobileNumber(String mobileNumber) {
        ElementWrapper.wrap(mobile_numberLocator).sendKeys(mobileNumber, "Mobile Number Field");
    }

    public void clickCreateAccount() {
        ElementWrapper.wrap(createAccount).click("Create Account");
    }

    public boolean accountCreationConfirmationIsDisplayed(){
        return ElementWrapper.wrap(accountCreationConfirmation).isDisplayed("Account Creation Confirmation");
    }

    public void clickContinue(){
        ElementWrapper.wrap(continueLocator).click("Continue");
    }
    public boolean accountDeletedConfirmationIsDisplayed(){
        return ElementWrapper.wrap(accountDeletedConfirmation).isDisplayed("Account Deleted Confirmation");
    }
}

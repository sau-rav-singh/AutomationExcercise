package tests;

import com.github.javafaker.Faker;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginSignupPage;
import pages.SignUpPage;
import utilities.CustomAssertions;
import utilities.GenericUtilities;
import utilities.TestBase;

@Listeners({listeners.ExtentReportConfigListener.class})
public class TestCases extends TestBase {

    protected SignUpPage signUpPage;
    protected HomePage homePage;
    protected LoginSignupPage loginSignupPage;
    Faker faker = GenericUtilities.getFakerObject();

    public void initializePages() {
        signUpPage = pageObjectManager.getPage(SignUpPage.class);
        homePage = pageObjectManager.getPage(HomePage.class);
        loginSignupPage = pageObjectManager.getPage(LoginSignupPage.class);
    }

    @BeforeTest
    public void setUp() {
        initializePages();
    }

    @Test(description = "This test verifies a successful user signup flow with valid data")
    public void TC_01() {
        CustomAssertions.assertTrue(homePage.isHomePageLoaded(), "Verify home page is loaded");
        homePage.signupLoginClick();
        CustomAssertions.assertTrue(loginSignupPage.isNewUserSignUpDisplayed(), "Verify New User SignUp is Displayed");
        loginSignupPage.fillSignup(faker.name().fullName(), faker.internet().emailAddress());
        CustomAssertions.assertTrue(signUpPage.isFormHeaderDisplayed(), "Sign Up Page Header");
        signUpPage.selectMr();
        signUpPage.enterPassword("mech1234");
        signUpPage.enterFirstName(faker.name().firstName());
        signUpPage.enterAddress(faker.address().fullAddress());
        signUpPage.enterCity(faker.address().city());
        signUpPage.enterState(faker.address().state());
        signUpPage.enterLastName(faker.address().lastName());
        signUpPage.enterZipcode(faker.address().zipCode());
        signUpPage.enterMobileNumber(faker.phoneNumber().toString());
        signUpPage.clickCreateAccount();
        CustomAssertions.assertTrue(signUpPage.accountCreationConfirmationIsDisplayed(), "Account Creation Confirmation");
        signUpPage.clickContinue();
        CustomAssertions.assertTrue(homePage.isLoggedin(), "Logged In");
        homePage.deleteAccountClick();
        CustomAssertions.assertTrue(signUpPage.accountDeletedConfirmationIsDisplayed(), "Account Deleted");
        signUpPage.clickContinue();
    }
}

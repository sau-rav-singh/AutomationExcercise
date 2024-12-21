package tests;

import com.github.javafaker.Faker;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CustomAssertions;
import utilities.GenericUtilities;
import utilities.TestBase;

@Listeners(listeners.ExtentReportConfigListener.class)
public class TestCases extends TestBase {

    Faker faker = GenericUtilities.getFakerObject();

    @Test
    public void TC_01() {
        CustomAssertions.assertTrue(pageObjectManager.getHomePage().isHomePageLoaded(), "Verify home page is loaded");
        pageObjectManager.getHomePage().signupLoginClick();
        CustomAssertions.assertTrue(pageObjectManager.getloginSignupPage().isNewUserSignUpDisplayed(), "Verify New User SignUp is Displayed");
        pageObjectManager.getloginSignupPage().fillSignup(faker.name().fullName(), faker.internet().emailAddress());
    }
}

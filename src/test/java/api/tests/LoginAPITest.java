package api.tests;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.SignUpRequest;
import api.config.service.AuthService;

public class LoginAPITest {
    private AuthService authService;

    @BeforeClass
    public void setup() {
        authService = new AuthService();
    }

    @Test
    public void loginTest() {
        LoginRequest loginRequest = new LoginRequest("uday1234", "uday1234");
        Response authResponse = authService.login(loginRequest);
        LoginResponse loginResponse = authResponse.as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
    }

    @Test
    public void signupTest() {
        Faker faker = new Faker();
        SignUpRequest signUpRequest = SignUpRequest.builder().username(faker.name().username()).password(faker.internet().password()).email(faker.internet().emailAddress()).firstName(faker.name().firstName()).lastName(faker.name().lastName()).mobileNumber(faker.phoneNumber().phoneNumber()).build();

        Response signupResponse = authService.signup(signUpRequest);
        System.out.println(signupResponse.asString());
    }
}

package service;

import io.restassured.response.Response;
import pojo.LoginRequest;
import pojo.SignUpRequest;

public class AuthService extends BaseService{
    private static final String BASE_PATH="/api/auth/";

    public Response login(LoginRequest loginRequest){
        return postRequest(loginRequest,BASE_PATH+"login");
    }

    public Response signup(SignUpRequest signUpRequest){
        return postRequest(signUpRequest,BASE_PATH+"signup");
    }
}

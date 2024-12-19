package service;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {
    protected RequestSpecification requestSpecification;

    public BaseService() {
        this.requestSpecification = RestAssured.given().baseUri(ConfigManager.BASE_URI);
    }

    protected Response postRequest(Object loginRequest, String endpoint){
        return requestSpecification.header("Content-type", "application/json").body(loginRequest).post(endpoint);
    }

    protected Response getRequest(String endpoint){
        return requestSpecification.post(endpoint);
    }
}

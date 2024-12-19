package service;

import io.restassured.response.Response;

public class UserProfile extends BaseService{
    private static final String BASE_PATH="/api/users/";

    public Response profile(){
        return getRequest(BASE_PATH+"profile");
    }

}

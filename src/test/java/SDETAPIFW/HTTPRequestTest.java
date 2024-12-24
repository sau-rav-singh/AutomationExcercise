package SDETAPIFW;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HTTPRequestTest {

    Faker faker;

    @BeforeMethod
    void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        faker = new Faker();
    }

    @Test
    public void getUsers() {
                given()
                //.log().all()
                .queryParam("page", 2).when().get("users").then().log().all().assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Cache-Control", "max-age=14400").body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .body("data.size()", equalTo(6))
                .body("data.id", everyItem(notNullValue()))
                .body("data.email", everyItem(matchesPattern("^[\\w.%+-]+@[\\w.-]+\\.\\w{2,6}$")))
                .body("data.first_name", everyItem(notNullValue()))
                .body("data.last_name", everyItem(notNullValue()))
                .body("data.avatar", everyItem(startsWith("https://reqres.in/img/faces/")))
                .body("support.url", startsWith("https://contentcaddy.io"))
                .body("support.text", notNullValue()).extract().response().asString();
    }

    @Test
    public void createUser() {
        String userName = faker.name().username();
        String job = faker.job().position();

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("name", userName);
        dataMap.put("job", job);

        given().log().all().body(dataMap).header("Content-Type", "application/json").header("accept", "application/json")
                .log().all().when().post("/users").then().log().all().assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Control-Allow-Origin", "*")
                .header("X-Powered-By", "Express")
                .header("Etag", notNullValue())
                .body("name", equalTo(userName))
                .body("job", equalTo(job))
                .body("id", notNullValue())
                .body("createdAt", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"))
                .extract().response();
    }

    @Test
    public void updateUser() {
        String userName = faker.name().username();
        String job = faker.job().position();

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("name", userName);
        dataMap.put("job", job);

        Response createUserResponse=given().body(dataMap).header("Content-Type", "application/json").header("accept", "application/json").when().post("/users").then().log().all().assertThat().statusCode(201).extract().response();

        JsonPath js=new JsonPath(createUserResponse.asString());
        String id=js.getString("id");
        System.out.println("id is "+id);

        given().log().all().body(dataMap).header("Content-Type", "application/json").header("accept", "application/json")
                .log().all().when().put("/users/"+id).then().log().all().assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Access-Control-Allow-Origin", "*")
                .header("X-Powered-By", "Express")
                .header("Etag", notNullValue())
                .body("name", equalTo(userName))
                .body("job", equalTo(job))
                .body("updatedAt", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"))
                .extract().response();
    }

    @Test
    public void mockReponseTest() throws IOException {
        String response= new String(Files.readAllBytes(Paths.get("src/test/java/SDETAPIFW/mock.json")));
        JSONObject jsonObject=new JSONObject(response);
        System.out.println(jsonObject.get("page"));
        JSONArray data = jsonObject.getJSONArray("data");
        System.out.println("Data Array: " + data);
        for (int i = 0; i < data.length(); i++) {
            JSONObject record = data.getJSONObject(i);
            System.out.println("ID: " + record.getInt("id"));
        }
            JSONObject support=jsonObject.getJSONObject("support");
            System.out.println(support.get("url"));

    }
}

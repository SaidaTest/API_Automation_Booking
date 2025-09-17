package utilities;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginTest {
    public static void main(String[] args) {
        Map<String, String> body = new HashMap<>();
        body.put("username", "admin");
        body.put("password", "password");

        Response response = given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(body)
                .when()
                .post("https://automationintesting.online/api/auth/login");

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Response: " + response.asString());
    }
}

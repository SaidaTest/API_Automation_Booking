package stepDefinitions;

import context.TestContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LoginSteps {
    public Response response;
    public static String token;

    public TestContext context = new TestContext();  // initialize directly
    public LoginSteps() {
        // default constructor for Cucumber
        }

    @Given("I send a login request with username {string} and password {string}")
    public void i_send_login_request(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

         response = given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(body)
                .when()
                .post("https://automationintesting.online/api/auth/login");

        context.setResponse(response);
        context.setStatusCode(response.getStatusCode());

         token = response.jsonPath().getString("token");
        context.setToken(token);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Token: " + token);
        System.out.println("Token: " + context.getToken());
    }

    @Then("I am checking login response should be {int}")
    public void i_am_checking_login_response_should_be(Integer expectedCode) {
        assertEquals(expectedCode.intValue(), context.getStatusCode());
        System.out.println("✅ Verified login response: " + expectedCode);
    }

    @Then("I should receive a valid token")
    public void i_should_receive_a_valid_token() {
        System.out.println("✅ Token : " + context.getToken());
        assertNotNull("Token is null!", context.getToken());

    }

    @Then("I should receive a invalid token")
    public void i_should_receive_a_invalid_token() {
        System.out.println("✅ Token : " + context.getToken());
        assertNull("Token is null!", context.getToken());
    }


    @When("I create a room with details from {string}")
    public void i_create_a_room_with_details_from(String fileName) throws Exception {
        assertNotNull("Login token is missing!", context.getToken());

        System.out.println("Login Response: " + response.asString());
        System.out.println("Token from response: " + context.getToken());
        // Read JSON file from resources
        String jsonPath = "src/test/resources/testdata/" + fileName;
        String jsonBody = new String(Files.readAllBytes(Paths.get(jsonPath)));

        response = RestAssured.given()
                .baseUri("https://automationintesting.online")
                .basePath("/api/room")
                .header("Authorization", ""+ context.getToken()) // ✅ use token
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post();

        response.prettyPrint();
    }

    @Then("The room should be created successfully")
    public void the_room_should_be_created_successfully() {
        response.then().statusCode(201);

        // ✅ roomName is inside array "rooms[0]"
        String createdRoom = response.jsonPath().getString("rooms[0].roomName");
        assertThat(createdRoom, equalTo("009"));

        System.out.println("✅ Room created successfully with name: " + createdRoom);
    }


}

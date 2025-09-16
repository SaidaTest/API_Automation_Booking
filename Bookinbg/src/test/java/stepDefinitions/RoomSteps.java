package stepDefinitions;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RoomSteps {

    private final TestContext context;
    public TokenManager tokenManager;

    // ✅ Cucumber will inject this
    public RoomSteps(TestContext context) {
        this.context = context;
        this.tokenManager = new TokenManager(context);
    }

    @Given("I have a valid login token")
    public void i_have_a_valid_login_token() {
        Response response = context.getResponse();
        if (response == null) {
            tokenManager.ensureToken();
        }
    }

    @When("I send a request to create a room")
    public void i_send_a_request_to_create_a_room() {
        System.out.println("Token in RoomSteps: " + context.getToken()); // should print the same token
        if (context.getToken() == null) {
            tokenManager.ensureToken();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("roomName", "020");
        body.put("type", "Single"); // ✅ correct key
        body.put("accessible", true);
        body.put("description", "Please enter a description for this room");
        body.put("image", "https://www.mwtestconsultancy.co.uk/img/room1.jpg");
        body.put("roomPrice", "250"); // ✅ must be String, not int
        body.put("features", new String[]{"TV"});


        Response response = given()
                .relaxedHTTPSValidation()
                .header("Cookie", "token=" + context.getToken())  // ✅ Add cookie as header
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://automationintesting.online/api/room");


        context.setResponse(response);

        System.out.println("Room creation status: " + response.getStatusCode());
        System.out.println("Room creation body: " + response.getBody().asString());
    }
}

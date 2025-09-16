package stepDefinitions;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RoomSteps {

    private final TestContext context;
    public TokenManager tokenManager;
    private Response response;
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

        // 1️⃣ Create a room first
        Map<String, Object> room = new HashMap<>();
        room.put("roomName", "API-ROOM-" + System.currentTimeMillis());
        room.put("type", "Single");
        room.put("accessible", true);
        room.put("description", "API created room");
        room.put("image", "https://www.mwtestconsultancy.co.uk/img/room1.jpg");
        room.put("roomPrice", 110);
        room.put("features", new String[]{"TV"});

         response = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Cookie", "token=" + context.getToken()) // admin token
                .contentType(ContentType.JSON)
                .body(room)
                .post("https://automationintesting.online/api/room");

        //int roomId = response.jsonPath().get("roomid");
        Integer roomId = response.jsonPath().get("roomid");
        if (roomId != null) {
            System.out.println("✅ Created Room ID: " + roomId);
        }

        System.out.println("Room creation status: " + response.getStatusCode());
        System.out.println("Room creation body: " + response.getBody().asString());
    }
}

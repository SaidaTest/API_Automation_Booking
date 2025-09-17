package stepDefinitions;

import context.TestContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class BookingSteps {

    private final TestContext context;
    private Response response;
    public TokenManager tokenManager;

    public BookingSteps(TestContext context) {
        this.context = context; // share context if needed
        this.tokenManager = new TokenManager(context);
    }

    @Given("User hits endpoint {string}")
    public void user_hits_endpoint(String endpoint) {
        System.out.println("➡️ Booking API endpoint: " + endpoint);
    }

    @When("User books the room with the given details")
    public void user_books_the_room_with_the_given_details(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);

        Map<String, Object> body = new HashMap<>();
        body.put("roomid", 1); // assuming room id 1
        body.put("firstname", data.get("firstname"));
        body.put("lastname", data.get("lastname"));
        body.put("email", data.get("email"));
        body.put("phone", data.get("phone"));

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", data.get("checkin"));
        bookingDates.put("checkout", data.get("checkout"));
        body.put("bookingdates", bookingDates);

        response = given()
                .relaxedHTTPSValidation()
                .header("Referer", "https://automationintesting.online/reservation/1?checkin="
                        + data.get("checkin") + "&checkout=" + data.get("checkout"))
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://automationintesting.online/api/booking");

        context.setResponse(response);

        System.out.println("➡️ Booking Response Status: " + response.getStatusCode());
        response.prettyPrint();
    }

    @Then("The response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
    }

    @Then("Verify response status code should be {int}")
    public void verify_response_status_code_should_be(Integer expectedStatusCode) {

        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
    }

    @When("User requests the details of the room by room id")
    public void User_requests_the_details_of_the_room_by_room_id() {

         response = given()
                .relaxedHTTPSValidation()
                // If needed, include the cookie for auth:
                .cookie("token", context.getToken())
                .accept(ContentType.JSON)
                .when()
                .get("https://automationintesting.online/api/room");

// or for a specific room by roomid = 1
        Response responseOne = given()
                .relaxedHTTPSValidation()
                .cookie("token", context.getToken())
                .accept(ContentType.JSON)
                .when()
                .get("https://automationintesting.online/api/room/1");

// then assertions

        response.then().statusCode(200);
        System.out.println("Rooms Response: " + response.getBody().asString());

        // Basic validation (adjust according to actual response)
        response.then().statusCode(200);
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
        room.put("roomPrice", 221);
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

    @When("I am checking the details of the room by room id {int}")
    public void i_am_the_details_of_the_room_by_room_id(Integer roomId) {
        response = RestAssured.given()
                .relaxedHTTPSValidation()
                .get("https://automationintesting.online/api/room/" + roomId);

        context.setResponse(response);

        System.out.println("➡️ Room Fetch Status: " + response.getStatusCode());
        System.out.println("➡️ Room Fetch Body: " + response.getBody().asString());
    }


    @Then("I verify response status code should be {int}")
    public void i_verify_response_status_code_should_be(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
    }

    @Then("I verify the response with json schema {string}")
    public void validate_the_response_with_json_schema(String schemaFileName) {
        InputStream schemaStream = getClass().getClassLoader()
                .getResourceAsStream("D:\\GitHubAPI\\Bookinbg\\src\\test\\resources\\schemas\\getbookingresponseschema.json");

        if (schemaStream == null) {
            throw new RuntimeException("❌ Schema file not found in classpath!");
        } else {
            System.out.println("✅ Schema file loaded successfully.");
        }

        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }
    @When("I update booking with id {int}")
    public void i_update_booking_with_id(Integer bookingId) {
        System.out.println("🔄 Updating booking with ID: " + bookingId);

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2026-03-09");
        bookingDates.put("checkout", "2026-03-10");

        Map<String, Object> body = new HashMap<>();
        body.put("bookingid", bookingId);
        body.put("roomid", 1);
        body.put("firstname", "Jamestses1");
        body.put("lastname", "Dean1");
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);

        Response response = given()
                .relaxedHTTPSValidation()
                .header("Cookie", "token=" + context.getToken())  // ✅ add token here
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("https://automationintesting.online/api/room/" + bookingId);

        context.setResponse(response);

        System.out.println("📩 Booking update response status: " + response.getStatusCode());
        System.out.println("📩 Booking update response body: " + response.getBody().asString());
    }

    @Then("the booking should be updated successfully")
    public void the_booking_should_be_updated_successfully() {
        Response response = context.getResponse();
        assertEquals(200, response.getStatusCode());

        String updatedName = response.jsonPath().getString("firstname");
        System.out.println("✅ Booking updated. New firstname: " + updatedName);
    }
    @When("The user deletes booking id 2")
    public void the_user_deletes_booking_id_7() {
        String token = context.getToken();
        if (token == null) {
            throw new RuntimeException("❌ Token is missing. Please login first.");
        }

        response = given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .cookie("token", token)
                .when()
                .delete("https://automationintesting.online/api/booking/1");

        System.out.println("➡️ Delete Response Status: " + response.getStatusCode());
        System.out.println("➡️ Delete Response Body: " + response.asString());
    }

    @Then("The booking should be deleted successfully")
    public void the_booking_should_be_deleted_successfully() {
        response.then().statusCode(200); // API usually returns 200 or 204 after deletion
    }



}

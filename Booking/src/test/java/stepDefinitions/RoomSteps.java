//package stepDefinitions;
//
//import context.TestContext;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import io.restassured.module.jsv.JsonSchemaValidator;
//import io.cucumber.java.en.Then;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
//
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.Assert.assertEquals;
//
//public class RoomSteps {
//
//    private final TestContext context;
//    public TokenManager tokenManager;
//    private Response response;
//    // ✅ Cucumber will inject this
//    public RoomSteps(TestContext context) {
//        this.context = context;
//        this.tokenManager = new TokenManager(context);
//    }
//
////    @Given("I have a valid login token")
////    public void i_have_a_valid_login_token() {
////        Response response = context.getResponse();
////        if (response == null) {
////            tokenManager.ensureToken();
////        }
////    }
//
//    @When("I send a request to create a room")
//    public void i_send_a_request_to_create_a_room() {
//        System.out.println("Token in RoomSteps: " + context.getToken()); // should print the same token
//        if (context.getToken() == null) {
//            tokenManager.ensureToken();
//        }
//
//        // 1️⃣ Create a room first
//        Map<String, Object> room = new HashMap<>();
//        room.put("roomName", "API-ROOM-" + System.currentTimeMillis());
//        room.put("type", "Single");
//        room.put("accessible", true);
//        room.put("description", "API created room");
//        room.put("image", "https://www.mwtestconsultancy.co.uk/img/room1.jpg");
//        room.put("roomPrice", 221);
//        room.put("features", new String[]{"TV"});
//
//         response = RestAssured.given()
//                .relaxedHTTPSValidation()
//                .header("Cookie", "token=" + context.getToken()) // admin token
//                .contentType(ContentType.JSON)
//                .body(room)
//                .post("https://automationintesting.online/api/room");
//
//        //int roomId = response.jsonPath().get("roomid");
//        Integer roomId = response.jsonPath().get("roomid");
//        if (roomId != null) {
//            System.out.println("✅ Created Room ID: " + roomId);
//        }
//
//        System.out.println("Room creation status: " + response.getStatusCode());
//        System.out.println("Room creation body: " + response.getBody().asString());
//    }
//
////    @When("I am checking the details of the room by room id {int}")
////    public void i_am_the_details_of_the_room_by_room_id(Integer roomId) {
////             response = RestAssured.given()
////                    .relaxedHTTPSValidation()
////                    .get("https://automationintesting.online/api/room/" + roomId);
////
////            context.setResponse(response);
////
////            System.out.println("➡️ Room Fetch Status: " + response.getStatusCode());
////            System.out.println("➡️ Room Fetch Body: " + response.getBody().asString());
////        }
//
//
////    @Then("I verify response status code should be {int}")
////    public void the_response_status_code_should_be(Integer expectedStatusCode) {
////        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
////    }
//
//    @Then("I verify the response with json schema {string}")
//    public void validate_the_response_with_json_schema(String schemaFileName) {
//        InputStream schemaStream = getClass().getClassLoader()
//                .getResourceAsStream("D:\\GitHubAPI\\Bookinbg\\src\\test\\resources\\schemas\\getbookingresponseschema.json");
//
//        if (schemaStream == null) {
//            throw new RuntimeException("❌ Schema file not found in classpath!");
//        } else {
//            System.out.println("✅ Schema file loaded successfully.");
//        }
//
//        response.then()
//                .assertThat()
//                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
//    }
//
//    // ✅ Hardcoded bookingId
//    private final int bookingId = 1;  // Change as needed
//
//    // ✅ Edit booking
//    @When("The user edits the booking details")
//    public void editBooking(io.cucumber.datatable.DataTable dataTable) {
//        Map<String, String> bookingData = dataTable.asMaps().get(0);
//
//        String token = context.getToken();  // Still dynamic from login
//
//        if (token == null) {
//            throw new RuntimeException("❌ Missing token. Did you login?");
//        }
//
//        // Build request body
//        Map<String, Object> bookingDetails = new HashMap<>();
//        bookingDetails.put("firstname", bookingData.get("firstname"));
//        bookingDetails.put("lastname", bookingData.get("lastname"));
//        bookingDetails.put("email", bookingData.get("email"));
//        bookingDetails.put("phone", bookingData.get("phone"));
//
//        Map<String, String> bookingDates = new HashMap<>();
//        bookingDates.put("checkin", bookingData.get("checkin"));
//        bookingDates.put("checkout", bookingData.get("checkout"));
//
//        bookingDetails.put("bookingdates", bookingDates);
//
//        response = given()
//                .relaxedHTTPSValidation()
//                .header("Content-Type", "application/json")
//                .cookie("token", token)
//                .body(bookingDetails)
//                .when()
//                .put("https://automationintesting.online/booking/" + bookingId);
//
//        context.setResponse(response);
//        System.out.println("✅ Booking updated: " + response.getBody().asPrettyString());
//    }
//
//
//    // ✅ Delete booking
//    @When("The user deletes the booking with booking ID")
//    public void deleteBooking() {
//        String token = context.getToken();
//
//        if (token == null) {
//            throw new RuntimeException("❌ Missing token. Did you login?");
//        }
//
//        response = given()
//                .relaxedHTTPSValidation()
//                .header("Content-Type", "application/json")
//                .cookie("token", token)
//                .when()
//                .delete("https://automationintesting.online/booking/" + bookingId);
//
//        context.setResponse(response);
//        System.out.println("🗑 Booking deleted, Response: " + response.getStatusCode());
//    }
//
//
//
//}

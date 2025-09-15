//package stepDefinitions;
//
//import io.cucumber.java.en.*;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import org.json.JSONObject;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//
//public class RoomSteps {
//
//    private static String token;
//    private Response response;
//
//    @Given("I have a valid login token")
//    public void i_have_a_valid_login_token() {
//        String loginBody = "{ \"username\":\"admin\", \"password\":\"password\" }";
//
//        Response loginResponse = RestAssured.given()
//                .baseUri("https://automationintesting.online")
//                .basePath("/api/auth/login")
//                .contentType(ContentType.JSON)
//                .body(loginBody)
//                .when()
//                .post();
//
//        loginResponse.then().statusCode(200);
//
//        token = loginResponse.jsonPath().getString("token");
//        System.out.println("Generated Token: " + token);
//    }
//
//    @When("I create a room with details from {string}")
//    public void i_create_a_room_with_details_from(String fileName) throws Exception {
//        // Read JSON file from resources
//        String jsonPath = "src/test/resources/testdata/" + fileName;
//        String jsonBody = new String(Files.readAllBytes(Paths.get(jsonPath)));
//
//        response = RestAssured.given()
//                .baseUri("https://automationintesting.online")
//                .basePath("/api/room")
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(jsonBody)
//                .when()
//                .post();
//
//        response.prettyPrint();
//    }
//
//    @Then("the room should be created successfully")
//    public void the_room_should_be_created_successfully() {
//        response.then().statusCode(201);
//
//        String createdRoom = response.jsonPath().getString("roomName");
//        assertThat(createdRoom, equalTo("009"));
//    }
//}

package stepDefinitions;

import context.TestContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BookingSteps {

    private final TestContext context;
    private Response response;

    public BookingSteps(TestContext context) {
        this.context = context; // share context if needed
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

        response = RestAssured.given()
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
}

package stepDefinitions;

import context.TestContext;
import io.restassured.response.Response;

import static org.junit.Assert.assertNotNull;

public class TokenManager {
    public static String token;
    private final TestContext context;
    public Response response;

    public TokenManager(TestContext context) {
        this.context = context;
    }

    public void ensureToken() {
        if (context.isTokenExpired()) {
            loginAgain();
        }
    }

    private String loginAgain() {
        // call LoginSteps logic or API to get a new token
        System.out.println("Token expired, generating a new one...");
        Response response = context.getResponse();

        if (response == null) {
            // Auto login if not already done
            response = io.restassured.RestAssured.given()
                    .relaxedHTTPSValidation()
                    .header("Content-Type", "application/json")
                    .body("{\"username\":\"admin\", \"password\":\"password\"}")
                    .when()
                    .post("https://automationintesting.online/api/auth/login");

            context.setResponse(response);
            context.setToken(response.jsonPath().getString("token"));
        }

        response.then().statusCode(200);
        assertNotNull("Token is missing!", context.getToken());

        System.out.println("✅ Generated Token: " + context.getToken());

        return context.getToken();
    }
}

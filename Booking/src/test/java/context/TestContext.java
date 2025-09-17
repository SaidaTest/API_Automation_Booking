package context;

import io.restassured.response.Response;

public class TestContext {
    private Response response;
    private String token;
    private int statusCode;
    private long tokenExpiryTime;

    public Response getResponse() {
        return response;
    }

    // --- Response ---
    public void setResponse(Response response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    // --- Token ---
    public void setToken(String token) {
        this.token = token;
    }

    public int getStatusCode() {
        return statusCode;
    }

    // --- Status Code ---
    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public void setTokenExpiryTime(long expiresInSeconds) {
        this.tokenExpiryTime = System.currentTimeMillis() + (expiresInSeconds * 1000);
    }

    public boolean isTokenExpired() {
        return System.currentTimeMillis() > tokenExpiryTime;
    }


}


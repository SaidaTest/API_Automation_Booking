Feature: Admin Login API Testing

  @HappyFlow
  Scenario: Verify login API returns 200 and token
    Given I send a login request with username "admin" and password "password"
    Then I am checking login response should be 200
    Then I should receive a valid token

  @InvalidParams
  Scenario: Verify login with invalid credentials
    Given I send a login request with username "admin1" and password "password"
    Then I am checking login response should be 401
    Then I should receive a invalid token

  @MissingMandatoryParams
  Scenario: Verify login with valid User credentials
    Given I send a login request with username "admin" and password "0"
    Then I am checking login response should be 401
    Then I should receive a invalid token





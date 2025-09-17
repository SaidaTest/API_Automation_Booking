Feature: Room API Testing

  @HappyFlow @regressionTesting
  Scenario: Verify login API returns 200 and token
    Given I send a login request with username "admin" and password "password"
    Then I am checking login response should be 200
    Then I should receive a valid token
    Given I have a valid login token
    When I send a request to create a room
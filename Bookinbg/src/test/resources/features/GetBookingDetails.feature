@getbooking @hotelbookingregressiontests
Feature: Get booking details

Background:
	Given User hits endpoint "api/auth/login"
  Given I send a login request with username "admin" and password "password"
  Then I am checking login response should be 200
  Then I should receive a valid token
  Given I have a valid login token
  When I send a request to create a room

@getroomdetails @test
Scenario: Get the details of the room by room id
	Given User hits endpoint "api/booking"
	When I am checking the details of the room by room id 1
	Then I verify response status code should be 200


  @performschemavalidation
  Scenario: Get the details of the room by room id and perform schema validation
    Given User hits endpoint "api/booking"
    When I am checking the details of the room by room id 1
    Then I verify response status code should be 200
    Then I verify the response with json schema "roomBooking.json"



@business @hotelbookingregressiontests
Feature: Test end to end flow

  Background:
    When I send a login request with username "admin" and password "password"
    Then I am checking login response should be 200
    Then I should receive a valid token
    Given I have a valid login token
    When I send a request to create a room

  @regressionTesting @hotelBooking
  Scenario Outline: Test end to end flow
    Given User hits endpoint "api/booking"
    And User books the room with the given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then Verify response status code should be 200
    When User requests the details of the room by room id
    Then I verify response status code should be 200
    When I update booking with id 1
    Then the booking should be updated successfully
    Then The response status code should be 200
    When The user deletes booking id 2
    Then The booking should be deleted successfully

    Examples:
      | firstname | lastname | email               | phone       | checkin    | checkout   |
      | User      | five     | user.five@gmail.com | 46645895464 | 2025-09-29 | 2025-09-30 |
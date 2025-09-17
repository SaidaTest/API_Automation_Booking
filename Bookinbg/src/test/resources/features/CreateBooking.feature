@CreateBooking @hotelbookingregressiontests
Feature: Test create a room booking

  Background:
    Given I send a login request with username "admin" and password "password"
    Then I am checking login response should be 200
    Then I should receive a valid token
    Given I have a valid login token
    When I send a request to create a room

  @HappyFlow
  Scenario Outline: Create a room booking
    Given User hits endpoint "api/booking"
    When User books the room with the given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then The response status code should be 200

    Examples:
      | firstname | lastname | email               | phone       | checkin    | checkout   |
      | User      | five     | user.five@gmail.com | 46645895464 | 2025-09-16 | 2025-09-19 |
      | User      | two      | user.two@gmail.com  | 77775895464 | 2025-10-16 | 2025-10-18 |
      | User      | one      | user.one@gmail.com  | 46666895464 | 2025-11-16 | 2025-11-19 |


  @InvalidParams
  Scenario Outline: Create a room booking with missing data
    Given User hits endpoint "api/booking"
    When User books the room with the given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then The response status code should be 400

    Examples:
      | firstname | lastname | email               | phone       | checkin  | checkout   |
      |           | five     | user.five@gmail.com | 46645895464 | 2025-09- | 2025-09-19 |

  @MissingMandatoryParams
  Scenario Outline: Create a room booking with missing data
    Given User hits endpoint "api/booking"
    When User books the room with the given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then The response status code should be 400

    Examples:
      | firstname | lastname | email               | phone       | checkin  | checkout   |
      |           | five     | user.five@gmail.com | 46645895464 | 2025-09- | 2025-09-19 |



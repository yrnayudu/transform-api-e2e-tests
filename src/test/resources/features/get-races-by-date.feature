Feature: Get Races/Meetings By Date Endpoint

  @e2e
  Scenario: Races/Meetings details retrieved by the racing API matches to the core api data
    Given A date to retrieve Races and Meetings
    When GET /racecards by the date
    Then Response contains all meetings from USA and FRA
    And Meeting details data matches to the core api data
    And Races count matches to the core api data
    And Race details information matches to the core api data





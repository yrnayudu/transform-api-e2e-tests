Feature: Get Racecard By ID Endpoint

  @e2e
  Scenario: Racecard header details retrieved by the racing API matches to the core api data
    Given A race ID
    When GET /racecard by ID
    Then Racecard header details matches to the core api data

  @e2e
  Scenario: Racecard runner details retrieved by the racing API matches to the core api data
    Given A race ID
    When GET /racecard by ID
    Then Racecard runner details matches to the core api data

  @e2e
  Scenario: Distance Conversion
    Given A race ID
    When GET /racecard by ID
    Then I get a response with a Distance string in the format miles and furlongs

  @e2e
  Scenario: Weight Conversion
    Given A race ID
    When GET /racecard by ID
    Then I get a response with a Weight string in the format stones and lbs

  @e2e
  Scenario: Headgear Conversion
    Given A race ID
    When GET /racecard by ID
    Then I get a response with a headgear array including as many headgear strings as headgear elements are included as part of the runner record

  @e2e @wip
  Scenario: Reference data lookup
    Given A race ID
    When GET /racecard by ID
    Then RaceType value in the response is the Master Value coming from the reference data
    And SurfaceType value in the response is the Master Value coming from the reference data
    And Going value in the response is the Master Value coming from the reference data

  @unit
  Scenario: Non-Runner
    Given A horse runner in the race is a non-runner
    When GET /racecard by race ID
    Then I get a response with 'jockey' = 'Non Runner', 'Runner Number' = 'NR' and 'Draw' = null




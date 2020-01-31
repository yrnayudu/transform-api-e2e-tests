package com.racingpost.racing.api.e2e.tests.steps;

import com.racingpost.racing.api.e2e.tests.clients.CoreAPIClient;
import com.racingpost.racing.api.e2e.tests.clients.RacingAPIClient;
import com.racingpost.racing.api.e2e.tests.model.RaceDetails;
import com.racingpost.racing.api.e2e.tests.utils.DateUtils;
import io.cucumber.java8.En;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class RacesByDateSteps implements En {

    private static final String CORE_API_DATE_FORMAT = "yyyyMMdd";
    private static final String RACING_API_DATE_FORMAT = "yyyy-MM-dd";
    private String racesJsonFromApi;
    private String racesJsonFromCore;
    private String dateParam;

    private CoreAPIClient coreAPIClient = new CoreAPIClient();
    private RacingAPIClient racingAPIClient = new RacingAPIClient();

    public RacesByDateSteps() {

        Before(() -> {
            racesJsonFromCore = coreAPIClient.getRaces(DateUtils.todayDateToString(CORE_API_DATE_FORMAT));
        });

        Given("A date to retrieve Races and Meetings", () -> {
            dateParam = DateUtils.todayDateToString(RACING_API_DATE_FORMAT);
        });

        When("GET /racecards by the date", () -> {
            racesJsonFromApi = racingAPIClient.getRaces(dateParam);
        });

        Then("Response contains all meetings from USA and FRA", () -> {

            List<String> racingMeetingIds = racingAPIClient.getAllMeetingIds(racesJsonFromApi);
            List<String> coreMeetingIds = coreAPIClient.getAllMeetingIds(racesJsonFromCore);
            assertThat(racingMeetingIds, containsInAnyOrder(coreMeetingIds.toArray()));
        });


        Then("Meeting details data matches to the core api data", () -> {
            String meetingId = racingAPIClient.getAMeetingId(racesJsonFromApi);
            String racingMeetingName = racingAPIClient.getMeetingName(racesJsonFromApi, meetingId);
            String coreMeetingName = coreAPIClient.getMeetingName(racesJsonFromCore, meetingId);
            assertThat(racingMeetingName, equalTo(coreMeetingName));

        });

        Then("Races count matches to the core api data", () -> {
            int raceCount = racingAPIClient.getRacesCount(racesJsonFromApi);
            int coreRaceCount = coreAPIClient.getRacesCount(racesJsonFromCore);
            assertThat(raceCount, equalTo(coreRaceCount));
        });

        Then("Race details information matches to the core api data", () -> {
            String raceId = racingAPIClient.getARaceId(racesJsonFromApi);
            RaceDetails racingApiRaceDetails = racingAPIClient.getRaceDetails(racesJsonFromApi, raceId);
            RaceDetails coreApiRaceDetails = coreAPIClient.getRaceDetails(racesJsonFromCore, raceId);

            assertThat(racingApiRaceDetails.getName(), equalTo(coreApiRaceDetails.getName()));
            assertThat(racingApiRaceDetails.getNumber_of_runners(), equalTo(coreApiRaceDetails.getNumber_of_runners()));
            assertThat(racingApiRaceDetails.getStart_time(), equalTo(coreApiRaceDetails.getStart_time()));
        });

    }

}

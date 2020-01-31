package com.racingpost.racing.api.e2e.tests.steps;

import com.racingpost.racing.api.e2e.tests.clients.CoreAPIClient;
import com.racingpost.racing.api.e2e.tests.clients.RacingAPIClient;
import com.racingpost.racing.api.e2e.tests.clients.RefDataClient;
import com.racingpost.racing.api.e2e.tests.model.RacecardHeader;
import com.racingpost.racing.api.e2e.tests.model.RunnerDetails;
import com.racingpost.racing.api.e2e.tests.utils.DateUtils;
import io.cucumber.java8.En;
import org.exparity.hamcrest.date.DateMatchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RacecardByIdSteps implements En {

    private String racesJsonFromApi;
    private String racecardJsonFromApi;
    private String racecardJsonFromCore;
    private String raceId;

    private static final String RACING_API_DATE_FORMAT = "yyyy-MM-dd";

    private CoreAPIClient coreAPIClient = new CoreAPIClient();
    private RacingAPIClient racingAPIClient = new RacingAPIClient();

    public RacecardByIdSteps() {


        Before(() -> {
            System.out.println("BEFORE CALLED");
            racesJsonFromApi = racingAPIClient.getRaces(DateUtils.todayDateToString(RACING_API_DATE_FORMAT));
            //racesJsonFromApi = racingAPIClient.getRaces("2020-01-22");
            RefDataClient.getAndPopulateRefData();
        });

        Given("A race ID", () -> {
            raceId = racingAPIClient.getARaceId(racesJsonFromApi);
            System.out.println("FRA RACE ID : " + raceId);
        });

        When("GET \\/racecard by ID", () -> {
            racecardJsonFromApi = racingAPIClient.getRacecard(raceId);
            racecardJsonFromCore = coreAPIClient.getRacecard(raceId);
        });

        Then("Racecard header details matches to the core api data", () -> {
            RacecardHeader racecardHeaderFromApi = racingAPIClient.getRacecardHeaderDetails(racecardJsonFromApi);
            RacecardHeader racecardHeaderFromCore = coreAPIClient.getRacecardHeaderDetails(racecardJsonFromCore);

            assertThat(racecardHeaderFromApi.getName(), equalTo(racecardHeaderFromCore.getName()));
            assertThat(DateUtils.stringToDate(racecardHeaderFromApi.getDate()), DateMatchers.sameInstant(DateUtils.stringToDate(racecardHeaderFromCore.getDate())));
            assertThat(racecardHeaderFromApi.getDescription(), equalTo(racecardHeaderFromCore.getDescription()));
            assertThat(DateUtils.stringToDate(racecardHeaderFromApi.getStart_time()), DateMatchers.sameInstant(DateUtils.stringToDate(racecardHeaderFromCore.getStart_time())));
            if(racecardHeaderFromApi.getCategory() != null)
                assertThat(racecardHeaderFromApi.getCategory(), containsInAnyOrder(racecardHeaderFromCore.getCategory().toArray()));
        });

        Then("Racecard runner details matches to the core api data", () -> {
            String runnerId = racingAPIClient.getARunnerId(racecardJsonFromApi);
            RunnerDetails runnerDetailsFromApi = racingAPIClient.getRacecardRunnerDetails(runnerId, racecardJsonFromApi);

            RunnerDetails runnerDetailsFromCore = coreAPIClient.getRacecardRunnerDetails(runnerId, racecardJsonFromCore);

            //assertThat(runnerDetailsFromApi.getHorse_age(), equalTo(runnerDetailsFromCore.getHorse_age()));
            assertThat(runnerDetailsFromApi.getHorse_name(), equalTo(runnerDetailsFromCore.getHorse_name()));
            assertThat(runnerDetailsFromApi.getJockey_name(), equalTo(runnerDetailsFromCore.getJockey_name()));
            assertThat(runnerDetailsFromApi.getRunner_number(), equalTo(runnerDetailsFromCore.getRunner_number()));
            assertThat(runnerDetailsFromApi.getTrainer_name(), equalTo(runnerDetailsFromCore.getTrainer_name()));

        });

        Then("I get a response with a Distance string in the format miles and furlongs", () -> {
            String distanceFromApi = racingAPIClient.getRaceDisplayDistance(racecardJsonFromApi);
            String distanceFromCore = coreAPIClient.getRaceDisplayDistance(racecardJsonFromCore);
            assertThat(distanceFromApi, equalTo(distanceFromCore));
        });

        Then("I get a response with a Weight string in the format stones and lbs", () -> {
            String runnerId = racingAPIClient.getARunnerId(racecardJsonFromApi);
            String weightFromApi = racingAPIClient.getRunnerWeight(runnerId, racecardJsonFromApi);
            String weightFromCore = coreAPIClient.getRunnerWeight(runnerId, racecardJsonFromCore);
            assertThat(weightFromApi, equalTo(weightFromCore));
        });


        Then("I get a response with a headgear array including as many headgear strings as headgear elements are included as part of the runner record", () -> {
            String runnerId = racingAPIClient.getARunnerId(racecardJsonFromApi);
            List<String> runnerHeadgearFromApi = racingAPIClient.getRunnerHeadgear(runnerId, racecardJsonFromApi);
            List<String> runnerHeadgearFromCore = coreAPIClient.getRunnerHeadgear(runnerId, racecardJsonFromCore);

            assertThat(runnerHeadgearFromApi, containsInAnyOrder(runnerHeadgearFromCore.toArray()));
        });

        Then("RaceType value in the response is the Master Value coming from the reference data", () -> {
            String raceTypeFromApi = racingAPIClient.getRaceType(racecardJsonFromApi);
            String raceTypeFromCore = coreAPIClient.getRaceType(racecardJsonFromCore);
            assertThat(raceTypeFromApi, equalTo(raceTypeFromCore));
        });

        Then("SurfaceType value in the response is the Master Value coming from the reference data", () -> {
            String surfaceTypeFromApi = racingAPIClient.getSurfaceType(racecardJsonFromApi);
            String surfaceTypeFromCore = coreAPIClient.getSurfaceType(racecardJsonFromCore);
            assertThat(surfaceTypeFromApi, equalTo(surfaceTypeFromCore));
        });

        Then("Going value in the response is the Master Value coming from the reference data", () -> {
            String goingValueFromApi = racingAPIClient.getGoingValue(racecardJsonFromApi);
            String goingValueFromCore = coreAPIClient.getGoingValue(racecardJsonFromCore);
            assertThat(goingValueFromApi, equalTo(goingValueFromCore));
        });




    }
}

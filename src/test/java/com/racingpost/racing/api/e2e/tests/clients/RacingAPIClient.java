package com.racingpost.racing.api.e2e.tests.clients;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.racingpost.racing.api.e2e.tests.model.RaceDetails;
import com.racingpost.racing.api.e2e.tests.model.RacecardHeader;
import com.racingpost.racing.api.e2e.tests.model.RunnerDetails;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RacingAPIClient {

    public String getRaces(String dateParam) {
        return given().when().get(String.format("%s?date=%s", Endpoints.RACING_API.getUrl(), dateParam)).andReturn().asString();
    }

    public String getRacecard(String raceId) {
        return given().when().get(String.format("%s/%s", Endpoints.RACING_API.getUrl(), raceId)).andReturn().asString();
    }

    public Integer getMeetingsCount(String racesJson) {

        List<String> meetingIds = getJsonDocument(racesJson).read("$.results[*].meetings[*].id");
        List<String> uniqueMeetingIds = meetingIds.stream().distinct().collect(Collectors.toList());
        return uniqueMeetingIds.size();
    }


    public String getAMeetingId(String racesJson) {
        return getJsonDocument(racesJson).read("$.results[0].meetings[0].id");
    }

    public String getMeetingName(String racesJson, String meetingId) {

        List<String> meetingName = getJsonDocument(racesJson).read(String.format("$.results[*].meetings[?(@.id == '%s')].name", meetingId));
        return meetingName.get(0);
    }

    public List<String> getAllMeetingIds(String racesJson) {
        return getJsonDocument(racesJson).read("$.results[*].meetings[*].id");
    }

    public Integer getRacesCount(String racesJson) {
        List allRaces = getJsonDocument(racesJson).read("$.results[*].meetings[*].races[*]");
        return allRaces.size();
    }

    public String getARaceId(String racesJson) {
        return getJsonDocument(racesJson).read("$.results[0].meetings[0].races[0].id");
    }

    public String getARaceId(String racingAPIResponse, String countryCode) {

        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(racingAPIResponse).read(String.format("$.results[?(@.country_code == '%s')].meetings[0].races[0].id", countryCode));
        return jsonArray.get(0).getAsString();
    }

    public RaceDetails getRaceDetails(String racingAPIResponse, String raceId) {

        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(racingAPIResponse).read(String.format("$.results[*].meetings[*].races[?(@.id == '%s')]", raceId));

        Type type = new TypeToken<List<RaceDetails>>() {
        }.getType();

        List<RaceDetails> races = new Gson().fromJson(jsonArray, type);
        return races.get(0);
    }


    public RacecardHeader getRacecardHeaderDetails(String racecardJson) {
        return new Gson().fromJson(racecardJson, RacecardHeader.class);
    }

    private DocumentContext getJsonDocument(String json) {
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);
        return JsonPath.using(conf).parse(json);
    }

    public String getARunnerId(String racecardJson) {
        return JsonPath.read(racecardJson, "$.runners[0].uid");
    }

    public RunnerDetails getRacecardRunnerDetails(String runnerId, String racecardJsonFromApi) {

        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(racecardJsonFromApi).read(String.format("$.runners[?(@.uid == '%s')]", runnerId));

        Type type = new TypeToken<List<RunnerDetails>>() {
        }.getType();

        List<RunnerDetails> runners = new Gson().fromJson(jsonArray, type);
        return runners.get(0);

    }

    private Configuration getGsonProviderConf() {
        return Configuration.builder().jsonProvider(new GsonJsonProvider())
                .mappingProvider(new GsonMappingProvider()).build();
    }

    public String getRaceDisplayDistance(String racecardJson) {
        return getJsonDocument(racecardJson).read("$.display_distance");
    }

    public List<String> getRunnerHeadgear(String runnerId, String racecardJson) {
        return getJsonDocument(racecardJson).read(String.format("$.runners[?(@.uid == '%s')].gear[*]", runnerId));
    }

    public String getRunnerWeight(String runnerId, String racecardJson) {
        List<String> weight = getJsonDocument(racecardJson).read(String.format("$.runners[?(@.uid == '%s')].weight", runnerId));
        return weight.size() > 0 ? weight.get(0): null;
    }

    public String getRaceType(String racecardJson) {
        return getJsonDocument(racecardJson).read("$.race_type");
    }

    public String getSurfaceType(String racecardJson) {
        return getJsonDocument(racecardJson).read("$.surface_type");
    }

    public String getGoingValue(String racecardJson) {
        return getJsonDocument(racecardJson).read("$.going");
    }
}

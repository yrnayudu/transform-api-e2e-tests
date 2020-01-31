package com.racingpost.racing.api.e2e.tests.clients;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.racingpost.racing.api.e2e.tests.model.RaceDetails;
import com.racingpost.racing.api.e2e.tests.model.RacecardHeader;
import com.racingpost.racing.api.e2e.tests.model.RunnerDetails;
import com.racingpost.racing.api.e2e.tests.utils.ConverterUtils;
import com.racingpost.racing.api.e2e.tests.utils.Gear;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class CoreAPIClient {

    public String getRaces(String date) {
        return given().when().get(String.format("%s?date=%s&page_number=0&page_size=200", Endpoints.CORE_API.getUrl(), date)).andReturn().asString();
    }

    public String getRacecard(String raceId) {
        return given().when().get(String.format("%s/%s?meta=false", Endpoints.CORE_API.getUrl(), raceId)).andReturn().asString();
    }

    public Integer getMeetingsCount(String json) {
        List<String> meetingIds = JsonPath.read(json, "$.results[*].core.meeting.uid");
        List<String> uniqueMeetingIds = meetingIds.stream().distinct().collect(Collectors.toList());
        return uniqueMeetingIds.size();
    }

    public List<String> getAllMeetingIds(String coreJson) {
        List<String> meetingIds = getJsonDocument(coreJson).read("$.results[*].core.meeting.uid");
        return meetingIds.stream().distinct().collect(Collectors.toList());
    }

    public String getMeetingName(String coreJson, String meetingId) {
        List<String> meetingNames = getJsonDocument(coreJson).read(String.format("$.results[*].core.meeting[?(@.uid == '%s')].core.venue.core.name.style", meetingId));
        return meetingNames.get(0);
    }

    public Integer getRacesCount(String coreJson) {
        List allRaces = getJsonDocument(coreJson).read("$.results[*]");
        return allRaces.size();
    }

    public RaceDetails getRaceDetails(String coreJson, String raceId) {
        RaceDetails raceDetails = new RaceDetails();
        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(coreJson).read(String.format("$.results[?(@.uid == '%s')]", raceId));
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        raceDetails.setId(jsonObject.get("uid").getAsString());
        raceDetails.setName(jsonObject.getAsJsonObject("core").getAsJsonObject("name").get("style").getAsString());
        raceDetails.setNumber_of_runners(jsonObject.getAsJsonObject("core").get("no_of_runners").getAsString());
        raceDetails.setStart_time(jsonObject.getAsJsonObject("core").get("start_time").getAsString());
        return raceDetails;
    }


    public RacecardHeader getRacecardHeaderDetails(String racecardJson) {
        DocumentContext document = getJsonDocument(racecardJson);
        RacecardHeader racecardHeader = new RacecardHeader();
        racecardHeader.setUid(document.read("$.uid"));
        racecardHeader.setName(document.read("$.core.name.style"));
        racecardHeader.setDate(document.read("$.core.start_scheduled_datetime_utc"));
        racecardHeader.setDescription(document.read("$.core.description"));
        racecardHeader.setStart_time(document.read("$.core.start_scheduled_datetime_utc"));
        racecardHeader.setCategory(document.read("$.core.category"));
        return racecardHeader;
    }

    private DocumentContext getJsonDocument(String json) {
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);
        return JsonPath.using(conf).parse(json);
    }

    public RunnerDetails getRacecardRunnerDetails(String runnerId, String racecardJson) {
        RunnerDetails runnerDetails = new RunnerDetails();

        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(racecardJson).read(String.format("$.runners[?(@.uid == '%s')]", runnerId));

        String runnerJson = new Gson().toJson(jsonArray.get(0));
        System.out.println("runner json :: " + runnerJson);

        runnerDetails.setUid(JsonPath.read(runnerJson, "$.uid"));
        runnerDetails.setHorse_age(JsonPath.read(runnerJson, "$.core.horse_age"));
        runnerDetails.setHorse_name(JsonPath.read(runnerJson, "$.core.name.style"));
        runnerDetails.setJockey_name(JsonPath.read(runnerJson, "$.core.jockey.core.name.style"));
        runnerDetails.setRunner_number(JsonPath.read(runnerJson, "$.core.runner_number"));
        runnerDetails.setTrainer_name(JsonPath.read(runnerJson, "$.core.horse.core.trainer.core.name.style"));

        return runnerDetails;
    }

    private Configuration getGsonProviderConf() {
        return Configuration.builder().jsonProvider(new GsonJsonProvider())
                .mappingProvider(new GsonMappingProvider()).build();
    }

    public String getRaceDisplayDistance(String racecardJson) {
        Integer distanceInYards = getJsonDocument(racecardJson).read("$.core.course.distance.yards");
        return ConverterUtils.yardsToFurlongs(distanceInYards);
    }

    public List<String> getRunnerHeadgear(String runnerId, String racecardJson) {
        List<String> headgear = new ArrayList<>();
        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(racecardJson).read(String.format("$.runners[?(@.uid == '%s')].core.equipment", runnerId));
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        if (jsonObject.get("status") != null && jsonObject.get("status").getAsBoolean())
            headgear.add(Gear.STATUS.getLabel());
        if (jsonObject.get("screens") != null && jsonObject.get("screens").getAsBoolean())
            headgear.add(Gear.SCREENS.getLabel());
        if (jsonObject.get("blinkers") != null && jsonObject.get("blinkers").getAsBoolean())
            headgear.add(Gear.BLINKERS.getLabel());
        if (jsonObject.get("cheekpiece") != null && jsonObject.get("cheekpiece").getAsBoolean())
            headgear.add(Gear.CHEEK_PIECE.getLabel());
        if (jsonObject.get("tongue_tie") != null && jsonObject.get("tongue_tie").getAsBoolean())
            headgear.add(Gear.TONGUE_TIE.getLabel());
        if (jsonObject.get("last_race_blinkers") != null && jsonObject.get("last_race_blinkers").getAsBoolean())
            headgear.add(Gear.LAST_RACE_BLINKERS.getLabel());
        if (jsonObject.get("last_race_cheekpiece") != null && jsonObject.get("last_race_cheekpiece").getAsBoolean())
            headgear.add(Gear.LAST_RACE_CHEEK_PIECE.getLabel());

        return headgear;
    }

    public String getRunnerWeight(String runnerId, String racecardJson) {
        List<Double> weightInLbs = getJsonDocument(racecardJson).read(String.format("$.runners[?(@.uid == '%s')].core.weight_carried.lbs", runnerId));
        return weightInLbs.size() > 0 ? ConverterUtils.lbsToStone(weightInLbs.get(0)) : null;
    }

    public String getRaceType(String racecardJson) {
        String raceTypeCode = getJsonDocument(racecardJson).read("$.core.type");
        return RefDataClient.getRaceTypeMasterValue(raceTypeCode);
    }

    public String getSurfaceType(String racecardJson) {
        String surfaceTypeCode = getJsonDocument(racecardJson).read("$.core.course.surface_code");
        return RefDataClient.getSurfaceTypeMasterValue(surfaceTypeCode);
    }

    public String getGoingValue(String racecardJson) {
        String goingCode = getJsonDocument(racecardJson).read("$.core.course.going_code");
        return RefDataClient.getGoingMasterValue(goingCode);
    }
}

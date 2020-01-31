package com.racingpost.racing.api.e2e.tests.clients;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RefDataClient {

    private static Map<String, String> raceTypeRefData = new HashMap<>();
    private static Map<String, String> surfaceTypeRefData = new HashMap<>();
    private static Map<String, String> goingRefData = new HashMap<>();

    private static void populateRaceTypeRefData(String refDataJson) {

        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(refDataJson).read("$.[?(@.field_type == 'race_type')]");

        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            raceTypeRefData.put(jsonObject.get("master_code").getAsString(), jsonObject.get("master_value").getAsString());
        });
    }

    private static void populateSurfaceTypeRefData(String refDataJson) {

        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(refDataJson).read("$.[?(@.field_type == 'surface_code')]");

        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            surfaceTypeRefData.put(jsonObject.get("master_code").getAsString(), jsonObject.get("master_value").getAsString());
        });
    }

    private static Configuration getGsonProviderConf() {
        return Configuration.builder().jsonProvider(new GsonJsonProvider())
                .mappingProvider(new GsonMappingProvider()).build();
    }

    private static String getReferenceData() {
        return given().when().get(Endpoints.REF_DATA.getUrl()).andReturn().asString();
    }

    public static void getAndPopulateRefData() {
        String refDataJson = getReferenceData();
        populateRaceTypeRefData(refDataJson);
        populateSurfaceTypeRefData(refDataJson);
        populateGoingRefData(refDataJson);
    }

    private static void populateGoingRefData(String refDataJson) {
        JsonArray jsonArray = JsonPath.using(getGsonProviderConf()).parse(refDataJson).read("$.[?(@.field_type == 'going_code')]");

        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            goingRefData.put(jsonObject.get("master_code").getAsString(), jsonObject.get("master_value").getAsString());
        });
    }

    static String getRaceTypeMasterValue(String raceTypeCode) {
        return raceTypeRefData.get(raceTypeCode);
    }

    static String getSurfaceTypeMasterValue(String raceTypeCode) {
        return surfaceTypeRefData.get(raceTypeCode);
    }

    public static String getGoingMasterValue(String goingCode) {
        return goingRefData.get(goingCode);
    }
}

package com.racingpost.racing.api.e2e.tests.model;

public class RaceDetails {

    private String id;
    private String name;
    private String number_of_runners;
    private String start_time;

    @Override
    public String toString() {
        return "RaceDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number_of_runners='" + number_of_runners + '\'' +
                ", start_time='" + start_time + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_of_runners() {
        return number_of_runners;
    }

    public void setNumber_of_runners(String number_of_runners) {
        this.number_of_runners = number_of_runners;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}

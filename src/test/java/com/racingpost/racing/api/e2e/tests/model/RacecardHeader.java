package com.racingpost.racing.api.e2e.tests.model;

import java.util.List;

public class RacecardHeader {
    private String uid;
    private String name;
    private String date;
    private String description;
    private String start_time;

    private List<String> category;


    @Override
    public String toString() {
        return "RacecardHeader{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", start_time='" + start_time + '\'' +
                ", category=" + category +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}

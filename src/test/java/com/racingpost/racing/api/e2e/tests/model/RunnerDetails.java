package com.racingpost.racing.api.e2e.tests.model;

public class RunnerDetails {
    private String uid;
    private Integer horse_age;
    private String horse_name;
    private String jockey_name;
    private Integer runner_number;
    private String trainer_name;

    @Override
    public String toString() {
        return "RunnerDetails{" +
                "uid='" + uid + '\'' +
                ", horse_age=" + horse_age +
                ", horse_name='" + horse_name + '\'' +
                ", jockey_name='" + jockey_name + '\'' +
                ", runner_number=" + runner_number +
                ", trainer_name='" + trainer_name + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getHorse_age() {
        return horse_age;
    }

    public void setHorse_age(Integer horse_age) {
        this.horse_age = horse_age;
    }

    public String getHorse_name() {
        return horse_name;
    }

    public void setHorse_name(String horse_name) {
        this.horse_name = horse_name;
    }

    public String getJockey_name() {
        return jockey_name;
    }

    public void setJockey_name(String jockey_name) {
        this.jockey_name = jockey_name;
    }

    public Integer getRunner_number() {
        return runner_number;
    }

    public void setRunner_number(Integer runner_number) {
        this.runner_number = runner_number;
    }

    public String getTrainer_name() {
        return trainer_name;
    }

    public void setTrainer_name(String trainer_name) {
        this.trainer_name = trainer_name;
    }
}

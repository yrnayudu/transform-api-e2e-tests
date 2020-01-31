package com.racingpost.racing.api.e2e.tests.utils;

public enum Gear {
    BLINKERS("blinkers"),
    CHEEK_PIECE("cheek piece"),
    LAST_RACE_CHEEK_PIECE("last race cheek piece"),
    FIRST_TIME_BLINKERS("first time blinkers"),
    LAST_RACE_BLINKERS("last race blinkers"),
    SCREENS("screens"),
    STATUS("status"),
    TONGUE_TIE("tongue tie");

    private String label;

    Gear(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

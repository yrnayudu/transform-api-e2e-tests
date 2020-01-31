package com.racingpost.racing.api.e2e.tests.clients;

public enum Endpoints {

    RACING_API("https://racing-api.ecs.dev.janus.rp-cloudinfra.com/racecards"),
    CORE_API("https://api-stable.data-nonprod.eu-west-2.aws.rp-cloudinfra.com/api/core/racecard"),
    REF_DATA("https://api-stable.data-nonprod.eu-west-2.aws.rp-cloudinfra.com/api/core/reference_data");

    private final String url;

    Endpoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

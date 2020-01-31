package com.racingpost.racing.api.e2e.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"classpath:features"},
        plugin = {"pretty", "json:target/jsonReports/cucumber.json"},
        tags = "@e2e",
        monochrome = true
)

public class RunCucumberTest {
}

package com.onepoint.kata.tennisgame;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-result"},
        features = {"classpath:features/"},
        glue = "com.onepoint.kata.tennisgame.steps"
)
public class RunCucumberTest {
}
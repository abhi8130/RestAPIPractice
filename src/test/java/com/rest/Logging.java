package com.rest;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Logging {

    //log().all()
    @org.testng.annotations.Test
    public void loggingGETRequest(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                log().all().
        when().
                get("/collections").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    //log().header
    @org.testng.annotations.Test
    public void loggingHeadersGETRequest(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                log().headers().
        when().
                get("/collections").
        then().
                assertThat().
                statusCode(200);
    }

    //log().ifError()
    @org.testng.annotations.Test
    public void loggingErrorGETRequest(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","MAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
        when().
                get("/collections").
        then().
                log().ifError().
                assertThat().
                statusCode(401);
    }

    //log().ifValidationFails()
    @org.testng.annotations.Test
    public void loggingErrorIfValidationFails(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                //log().ifValidationFails().
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
        when().
                get("/collections").
        then().
                //log().ifValidationFails().
                assertThat().
                statusCode(201);
    }

    //BlacklistHeaders
    @org.testng.annotations.Test
    public void blackListHeaders(){
        Set<String>  headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                //log().ifValidationFails().
                // blacklist single headers
                // config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))).

                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().headers().
        when().
                get("/collections").
        then().
                //log().ifValidationFails().
                        assertThat().
                statusCode(200);
    }





}

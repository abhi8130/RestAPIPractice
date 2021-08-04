package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class DefaultRequestSpecification {

   // RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.getpostman.com").
                addHeader("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                log(LogDetail.ALL);

        // requestSpecification: this static variable will be global & will retain its value throughout the program unless you overwrite/reset it
        RestAssured.requestSpecification = requestSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validateGETStatusCode(){
        Response response =  get("/collections").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
    }

    @org.testng.annotations.Test
    public void validateGETResponseBody(){
        Response response = get("/collections").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
        assertThat(response.<String>path("collections[1].name"),equalTo("BOLD API's"));
    }
}

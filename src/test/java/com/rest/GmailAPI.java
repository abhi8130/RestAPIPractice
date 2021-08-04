package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class GmailAPI {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.a0ARrdaM9NeDNzLW44kj2vhnGd399KF0R_iMU0eL8kBxSxmWhfDWHMCJ3vwG8loSS9pT_" +
            "9jRtFlm4tBDVHaOpMlqQsg6oZ87WkTXaezgEkjaMHXse3NkjHtJAeaHfvpu3BmHtWgXoNRU_A-4eHvkd2SOBjUXF5yw";

    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com/").
                addHeader("Authorization","Bearer "+ access_token);
                requestSpecification =  requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void getUserProfile(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userID","abhishek.chauhan@classplus.co").
        when().
                get("/users/{userID}/profile").
        then().spec(responseSpecification);
    }
}

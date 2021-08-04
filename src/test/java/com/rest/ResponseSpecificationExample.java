package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

public class ResponseSpecificationExample {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void BeforeClass(){
        requestSpecification = with().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4");
        responseSpecification = RestAssured.expect().
                statusCode(200).
                contentType(ContentType.JSON);
    }

    @org.testng.annotations.Test
    public void validateGETStatusCode(){
        given().spec(requestSpecification).
        when().
                get("/collections").
        then().spec(responseSpecification).
                log().all();
    }

    @org.testng.annotations.Test
    public void validateGETResponseBody(){
        given().spec(requestSpecification).
        when().
                get("/collections").
        then().spec(responseSpecification).
                log().all().
                assertThat().
                body("collections.name",hasItem("Postman API"));
    }
}

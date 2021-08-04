package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class RequestSpecificationExample {

    //To club common request specifications together and put as a common entity, we can use RequestSpecification in Rest Assured.
    // RequestSpecification is an interface that allows you to specify how the request will look like.
    // This interface has readymade methods to define base URL, base path, headers, etc. We need to use given()
    // method of RestAssured class to get a reference for RequestSpecification.
    // Remember RequestSpecification is an interface and we can not create an object of it.
    // RequestSpecificationImpl is its implemented class.

    RequestSpecification requestSpecification;

    @BeforeClass
    public void BeforeClass(){
         requestSpecification = with().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4");
    }

    @org.testng.annotations.Test
    public void validateGETStatusCode(){
        /*RequestSpecification requestSpecification = given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4");*/
        //given(requestSpecification).
        given().spec(requestSpecification).
        when().
                get("/collections").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void validateGETResponseBody(){
        given().spec(requestSpecification).
        when().
                get("/collections").
        then().
                assertThat().
                statusCode(200).
                body("collections.name",hasItems("Postman API", "BOLD API's", "WhiteLabel API", "PushNotification", "Chat-Engine Api", "Whitelabel_Login", "Notifications API", "Co-WIN Public APIs"),
                        "collections[1].name",equalTo("BOLD API's"),
                        "collections[2].name",is(equalTo("WhiteLabel API")),
                        "collections.size()",equalTo(9),
                        "collections.name",hasItem("Postman API"));
    }
}

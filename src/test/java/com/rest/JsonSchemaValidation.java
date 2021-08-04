package com.rest;

import io.restassured.specification.Argument;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation {

    @org.testng.annotations.Test
    public void json_schemas_vaildation(){
        given().
                baseUri("https://reqres.in").
                log().all().
                pathParam("userId",2).
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("ReqresGet.json"));
    }

}

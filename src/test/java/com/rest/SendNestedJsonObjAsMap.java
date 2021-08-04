package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class SendNestedJsonObjAsMap {
    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.getpostman.com").
                addHeader("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validate_post_request_payload_as_map(){
        HashMap<String,Object> mainObject = new HashMap<String, Object>();

        HashMap<String,String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name","My Third Workspace");
        nestedObject.put("type","personal");
        nestedObject.put("description","Payload Through Map");

        mainObject.put("workspace",nestedObject);
        Response response = with().
                body(mainObject).
                post("/workspaces");
        assertThat(response.<String>path("workspace.name"),equalTo("My Third Workspace"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));
    }
}

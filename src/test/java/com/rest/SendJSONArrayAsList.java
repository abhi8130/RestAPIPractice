package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class SendJSONArrayAsList {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").
                //setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                    // appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                //setContentType(ContentType.JSON).
               setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(201).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validate_post_request_payload_json_array_as_list(){
        HashMap<String,String> obj5001 = new HashMap<String, String>();
        obj5001.put("id","5001");
        obj5001.put("type","None");

        HashMap<String,String> obj5002 = new HashMap<String, String>();
        obj5002.put("id","5002");
        obj5002.put("type","Glazed");

        List<HashMap<String,String>> listObj = new ArrayList<HashMap<String, String>>();
        listObj.add(obj5001);
        listObj.add(obj5002);

        given().
                body(listObj).
        when().
                post("/post").
        then().spec(customResponseSpecification).
                body("msg",equalTo("successful"));
    }
}

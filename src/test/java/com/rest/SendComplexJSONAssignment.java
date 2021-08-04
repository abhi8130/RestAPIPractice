package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SendComplexJSONAssignment {
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
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validate_post_request_payload_json_array_as_list(){
        List<Integer> rgbaArray2 = new ArrayList<Integer>();
        rgbaArray2.add(0);
        rgbaArray2.add(0);
        rgbaArray2.add(0);
        rgbaArray2.add(1);

        //Value:Code
        HashMap<String,Object> codeValueHashMap2 = new HashMap<String, Object>();
        codeValueHashMap2.put("rgba",rgbaArray2);
        codeValueHashMap2.put("hex","#FFF");

        HashMap<String,Object> subHashMap2 = new HashMap<String, Object>();
        subHashMap2.put("color","white");
        subHashMap2.put("category","value");
        subHashMap2.put("code",codeValueHashMap2);

        List<Integer> rgbaArray1 = new ArrayList<Integer>();
        rgbaArray1.add(255);
        rgbaArray1.add(255);
        rgbaArray1.add(255);
        rgbaArray1.add(1);

        //Value:Code
        HashMap<String,Object> codeValueHashMap1 = new HashMap<String, Object>();
        codeValueHashMap1.put("rgba",rgbaArray1);
        codeValueHashMap1.put("hex","#000");

        HashMap<String,Object> subHashMap1 = new HashMap<String, Object>();
        subHashMap1.put("color","black");
        subHashMap1.put("category","hue");
        subHashMap1.put("type","primary");
        subHashMap1.put("code",codeValueHashMap1);

        List<HashMap<String,Object>> colorsArrayList = new ArrayList<HashMap<String, Object>>();
        colorsArrayList.add(subHashMap1);
        colorsArrayList.add(subHashMap2);

        HashMap<String,Object> mainHashMap = new HashMap<String, Object>();
        mainHashMap.put("colors",colorsArrayList);

        given().
                body(mainHashMap).
                when().
                post("/postComplexJson").
                then().spec(customResponseSpecification).
                body("msg",equalTo("successful"));
    }
}

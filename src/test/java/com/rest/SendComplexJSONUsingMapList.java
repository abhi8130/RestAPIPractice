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

public class SendComplexJSONUsingMapList {
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
        List<Integer> idArray = new ArrayList<Integer>();
        idArray.add(5);
        idArray.add(9);

        HashMap<String,Object> batterMap2 = new HashMap<String, Object>();
        batterMap2.put("id",idArray);
        batterMap2.put("type","Chocolate");

        HashMap<String,Object> batterMap1 = new HashMap<String, Object>();
        batterMap1.put("id","1001");
        batterMap1.put("type","Regular");

        // key : batters
        List<HashMap<String,Object>> batterArrayList = new ArrayList<HashMap<String, Object>>();
        batterArrayList.add(batterMap1);
        batterArrayList.add(batterMap2);

        HashMap<String,List<HashMap<String,Object>>> battersHashMap = new HashMap<String, List<HashMap<String, Object>>>();
        battersHashMap.put("batter",batterArrayList);

        HashMap<String,Object> toppingMap1 = new HashMap<String, Object>();
        toppingMap1.put("id","5001");
        toppingMap1.put("type","None");

        List<String> typeArray = new ArrayList<String>();
        typeArray.add("test1");
        typeArray.add("test2");

        HashMap<String,Object>  toppingMap2 = new HashMap<String, Object>();
        toppingMap2.put("id","5002");
        toppingMap2.put("type",typeArray);

        List<HashMap<String,Object>> toppingArrayList = new ArrayList<HashMap<String, Object>>();
        toppingArrayList.add(toppingMap1);
        toppingArrayList.add(toppingMap2);

        HashMap<String,Object> mainHashMap = new HashMap<String, Object>();
        mainHashMap.put("id","0001");
        mainHashMap.put("type","donut");
        mainHashMap.put("name","Cake");
        mainHashMap.put("ppu",0.55);
        mainHashMap.put("batters",battersHashMap);
        mainHashMap.put("topping", toppingArrayList);

        given().
                body(mainHashMap).
                when().
                post("/postComplexJson").
                then().spec(customResponseSpecification).
                body("msg",equalTo("successful"));
    }
}

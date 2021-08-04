package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class JacksonAPI_JsonArray {
    RequestSpecification requestSpecification;
    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(201).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void serialize_list_to_json_array() throws JsonProcessingException {

        HashMap<String,String> obj5001 = new HashMap<String, String>();
        obj5001.put("id", "5001");
        obj5001.put("type","None");

        HashMap<String,String> obj5002 = new HashMap<String, String>();
        obj5001.put("id", "5002");
        obj5001.put("type","Glazed");

        List<HashMap<String,String>> jsonList = new ArrayList<HashMap<String, String>>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectAsString = objectMapper.writeValueAsString(jsonList);

        given().
                body(mainObjectAsString).
        when().
                post("/post").
        then().
                assertThat().
                log().all().
                body("msg",equalTo("successful"));
    }

    @org.testng.annotations.Test
    public void serialize_jackson_array_node_to_json_array() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        ObjectNode object5001Node = objectMapper.createObjectNode();
        object5001Node.put("id", "5001");
        object5001Node.put("type","None");

        ObjectNode object5002Node = objectMapper.createObjectNode();
        object5002Node.put("id", "5002");
        object5002Node.put("type","Glazed");

        arrayNode.add(object5001Node);
        arrayNode.add(object5002Node);

        String jsonListAsString = objectMapper.writeValueAsString(arrayNode);

        given().
                body(jsonListAsString).
                when().
                post("/post").
                then().
                assertThat().
                log().all().
                body("msg",equalTo("successful"));
    }


}

package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class JacksonAPI_JsonObject {
    RequestSpecification requestSpecification;
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
    public void serialize_map_to_json_object() throws JsonProcessingException {

        HashMap<String,Object> mainObject = new HashMap<String, Object>();

        HashMap<String,String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name","MyWorkSpace1");
        nestedObject.put("type","personal");
        nestedObject.put("description","payload through map");

        mainObject.put("workspace",nestedObject);

        // here we are explicitly serialized to json/xml & sent with the request as a string
        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectAsString = objectMapper.writeValueAsString(mainObject);

        given().
                body(mainObjectAsString).
        when().
                post("/workspaces").
        then().
                assertThat().
                log().all().
                body("workspace.name",equalTo("MyWorkSpace1"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }

    @org.testng.annotations.Test
    public void serialize_jackson_object_node_to_json_object() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("name","MyWorkSpace3");
        objectNode.put("type","personal");
        objectNode.put("description","payload through map");

        ObjectNode mainObjectNode = objectMapper.createObjectNode();
        mainObjectNode.set("workspace",objectNode);

        String mainObjectAsString = objectMapper.writeValueAsString(mainObjectNode);

        given().
                body(mainObjectNode).
                when().
                post("/workspaces").
                then().
                assertThat().
                log().all().
                body("workspace.name",equalTo("MyWorkSpace3"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
}

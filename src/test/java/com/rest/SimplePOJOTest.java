package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePOJO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimplePOJOTest {
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
    public void simple_pojo_serialize_example(){
        //SimplePOJO simplePOJO = new SimplePOJO("value1","value2");
        SimplePOJO simplePOJO = new SimplePOJO();
        simplePOJO.setKey1("value1");
        simplePOJO.setKey2("value2");

/*        String payload = " {\n" +
                "        \"key1\": \"value1\",\n" +
                "        \"key2\": \"value2\"\n" +
                "}";*/

        given().
                body(simplePOJO).
        when().
                post("/postSimplePOJO").
        then().spec(customResponseSpecification).
                body("key1",equalTo(simplePOJO.getKey1()),
                        "key2",equalTo(simplePOJO.getKey2()));
    }

    @org.testng.annotations.Test
    public void simple_pojo_deserialize_example() throws JsonProcessingException {
        //SimplePOJO simplePOJO = new SimplePOJO("value1","value2");
        SimplePOJO simplePOJO = new SimplePOJO();
        simplePOJO.setKey1("value1");
        simplePOJO.setKey2("value2");

        SimplePOJO deserializePOJO = given().
                body(simplePOJO).
        when().
                post("/postSimplePOJO").
        then().spec(customResponseSpecification).
                extract().
                response().
                as(SimplePOJO.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String deserializePOJOStr = objectMapper.writeValueAsString(deserializePOJO);
        String serializePOJOStr = objectMapper.writeValueAsString(simplePOJO);

        assertThat(objectMapper.readTree(deserializePOJOStr),equalTo(objectMapper.readTree(serializePOJOStr)));
    }
}

package com.rest;

import io.restassured.config.EncoderConfig;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestParameters {

    @org.testng.annotations.Test
    public void single_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                log().all().
                //param("foo1","bar1").
                queryParam("foo1","bar1").
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void multiple_query_parameter(){
        HashMap<String,String> hmap = new HashMap<String, String>();
        hmap.put("foo1","bar1");
        hmap.put("foo2","bar2");

        given().
                baseUri("https://postman-echo.com").
                log().all().
                //queryParam("foo1","bar1").
                //queryParam("foo2","bar2").
                queryParams(hmap).
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void multiple_value_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                log().all().
                //param("foo1","bar1").
                        queryParam("foo1","bar1","bar2","bar3").
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void path_parameter(){
        given().
                baseUri("https://reqres.in").
                log().all().
                //param("foo1","bar1").
                        pathParam("userId",2).
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void form_parameters(){
        given().
                baseUri("https://postman-echo.com").
                log().all().
                multiPart("foo1","bar1").
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void form_UrlEncoded(){
        given().
                baseUri("https://postman-echo.com").
                log().all().
                config(config.encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                formParam("key1","value1").
                formParam("key 2", "value 2").
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

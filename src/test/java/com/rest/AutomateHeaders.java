package com.rest;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateHeaders {

    // multiple headers
    @org.testng.annotations.Test
    public void multipleHeaders(){

        Header header = new Header("header","value2");
        Header matchHeader = new Header("x-mock-match-request-headers","header");

        Set<String>  headers = new HashSet<String>();
        headers.add("header");
        headers.add("x-mock-match-request-headers");
        headers.add("Accept");
        given().
                baseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                // header("header","value2").
                //header("x-mock-match-request-headers","header").
                header(header).header(matchHeader).
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // multiple headers using Headers()
    @org.testng.annotations.Test
    public void multiple_headers_using_Headers(){

        Header header = new Header("header","value2");
        Header matchHeader = new Header("x-mock-match-request-headers","header");

        Headers headers = new Headers(header,matchHeader);
        given().
                baseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                headers(headers).
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // multiple headers using Map()
    @org.testng.annotations.Test
    public void multiple_headers_using_Map(){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");

        given().
                baseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                header("multiValueHeader","value1","value2").
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // assert response header
    @org.testng.annotations.Test
    public void assert_response_header(){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");
        given().
                baseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                headers(headers).
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200).
                //header("X-RateLimit-Limit","120").
                //header("Connection","keep-alive");
                headers("X-RateLimit-Limit","120","Connection","keep-alive");
    }

    // extract response headers
    @org.testng.annotations.Test
    public void extract_response_header(){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");
        Headers extractedHeaders = given().
                baseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                headers(headers).
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().headers();
                //extract all headers
                for(Header header : extractedHeaders)
                {
                    System.out.print("headerName = " + header.getName() + "  " + " ,");
                    System.out.println("headerValue = " + " " + header.getValue());
                }


                // extract header name & value
                /*System.out.println("headerName = " + " " + extractedHeaders.get("responseHeader").getName());
                System.out.println("headerValue = " + " " + extractedHeaders.get("responseHeader").getValue());*/
    }

    // extract multi value response headers
    @org.testng.annotations.Test
    public void extract_multi_value_response_header(){
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");
        Headers extractedHeaders = given().
                baseUri("https://e69552f0-d80f-478b-a3df-0430be8d7982.mock.pstmn.io").
                headers(headers).
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().headers();
                List<String> values = extractedHeaders.getValues("multiValueHeader");
                for(String value : values)
                {
                    System.out.println(value);
                }

    }
}

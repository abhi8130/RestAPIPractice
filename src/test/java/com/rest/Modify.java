package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class Modify {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.a0ARrdaM-hQQe81hlRv2KSTpQEKm9XVasOOtgu7PqyeA5LvYHIROk1ioq43qHtlQh5g" +
            "-BgHdnkXUk69XyvuEP2KPQDkUIah9WZePcwgKLcwuP3OAaOCeypowcBaXQFN4313dIUATGly5bj0c86LzQf0oU_oLEdtA";

    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com/").
                addHeader("Authorization","Bearer "+ access_token);
        requestSpecification =  requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void deleteMsg(){
        String getMsgId = given(requestSpecification).
                basePath("/gmail/v1").pathParam("userId","abhishek.chauhan@classplus.co").
                when().
                get("/users/{userId}/messages").
                then().spec(responseSpecification).extract().response().path("messages[0].id");
        System.out.println("MsgId: " + getMsgId);

        given(requestSpecification).
                basePath("/gmail/v1").pathParam("userId","abhishek.chauhan@classplus.co").
                pathParam("id",getMsgId).
        when().
                post("users/{userId}/messages/{id}/modify").
        then().
                spec(responseSpecification).statusCode(204);
    }
}

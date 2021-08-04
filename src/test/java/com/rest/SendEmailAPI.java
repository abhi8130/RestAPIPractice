package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SendEmailAPI {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.a0ARrdaM--tiDGSg0_Pt8x4toiRlCicUu8sVj-lCOYon0Jo74Q08J2XmhWVTfFWwc6Q0UV38MjeQPIwMFwfpROmjw0udl20ynqG5QpAfD-AtqCTjNZCegE33GTExOFD2RLaywM623fT6d8Ddg7NiTt9jADGppXTw";

    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com/").
                addHeader("Authorization","Bearer "+ access_token);
        requestSpecification =  requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void sendEmail(){
        String msg = "from:abhishek.chauhan@classplus.co\n" +
                "to:abhishek.chauhan@classplus.co\n" +
                "subject: Test Email\n" +
                "\n" +
                "Sending from GMAIL API";
        String base64EncodedStr = Base64.getUrlEncoder().encodeToString(msg.getBytes());

        HashMap<String,String> payload = new HashMap<String, String>();
        payload.put("raw",base64EncodedStr);

        String resPost = given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userID","abhishek.chauhan@classplus.co").
                body(payload).
        when().
                post("/users/{userID}/messages/send").
                then().spec(responseSpecification).
                extract().response().asString();
        JsonPath js = new JsonPath(resPost);
        String msgID = js.get("id").toString();
        System.out.println("id: " + msgID);

    }
}

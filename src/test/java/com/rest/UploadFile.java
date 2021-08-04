package com.rest;

import java.io.*;
import static io.restassured.RestAssured.given;

public class UploadFile {
    //using inputStream
    @org.testng.annotations.Test
    public void file_upload() throws IOException {
        given().
                baseUri("https://postman-echo.com").
                log().all().
                multiPart("file","temp.txt").
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}

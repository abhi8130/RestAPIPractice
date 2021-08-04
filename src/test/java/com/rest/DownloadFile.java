package com.rest;

import java.io.*;
import static io.restassured.RestAssured.given;

public class DownloadFile {

    @org.testng.annotations.Test
    public void file_download() throws IOException {
         byte[] bytes = given().
                baseUri("https://github.com").
                log().all().
        when().
                post("/appium/appium/raw/master/sample-code/apps/ApiDemos-debug.apk").
        then().
                log().all().
                extract().
                response().asByteArray();

        OutputStream outputStream = new FileOutputStream(new File("ApiDemos-debug.apk"));
        outputStream.write(bytes);
        outputStream.close();
        }
    //using inputStream
    @org.testng.annotations.Test
    public void file_download_input_stream() throws IOException {
        InputStream inputStream = given().
                baseUri("https://github.com").
                log().all().
        when().
                post("/appium/appium/raw/master/sample-code/apps/ApiDemos-debug.apk").
        then().
                log().all().
                extract().
                response().asInputStream();

        OutputStream outputStream = new FileOutputStream(new File("ApiDemos-debug.apk"));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        outputStream.write(bytes);
        outputStream.close();
        }
    }



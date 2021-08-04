package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Collections;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class AutomateGET {

    //Assert status code
    @org.testng.annotations.Test
    public void validateGETStatusCode(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
        when().
                get("/collections").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    //Assert response body
    @org.testng.annotations.Test
    public void validateGETResponseBody(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
        when().
                get("/collections").
        then().
                assertThat().
                statusCode(200).
                body("collections.name",hasItems("Postman API", "BOLD API's", "WhiteLabel API", "PushNotification", "Chat-Engine Api", "Whitelabel_Login", "Notifications API", "Co-WIN Public APIs"),
                        "collections[1].name",equalTo("BOLD API's"),
                        "collections[2].name",is(equalTo("WhiteLabel API")),
                        "collections.size()",equalTo(8),
                        "collections.name",hasItem("Postman API"));
    }

    //Extract response
    @org.testng.annotations.Test
    public void extractResponse(){
        Response response = given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
        when().
                get("/collections").
        then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("Response: " + response.asString());
    }

    //Extract a single value from the response
    @org.testng.annotations.Test
    public void extractSingleValueFromResponse_1(){
        Response response = given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                when().
                get("/collections").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        JsonPath jsonPath = new JsonPath(response.asString());
        //using jsonpath
        System.out.println("Collection Name: " + jsonPath.getString("collections[2].name"));

        //using path
        System.out.println("Collection Name: " + response.<String>path("collections[2].name"));
    }

    //Extract a single value from the response
    @org.testng.annotations.Test
    public void extractSingleValueFromResponse_2(){
        String response = given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                when().
                get("/collections").
                then().
                assertThat().
                statusCode(200).
                extract().
                //response().asString();
                response().path("collections[2].name");
        //Method 3:using jsonpath
       // System.out.println("Collection Name: " + JsonPath.from(response).getString("collections[2].name"));

        //Method 4: using path
        System.out.println("Collection Name: " + response);
    }

    //Hamcrest assertion on extracted response
    @org.testng.annotations.Test
    public void hamcrestAssertionExtractResponse(){
        String response = given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                when().
                get("/collections").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("collections[2].name");
        assertThat(response,equalTo("WhiteLabel API"));
        Assert.assertEquals(response, "WhiteLabel API");
    }

    //Hamcrest collections matches
    @org.testng.annotations.Test
    public void validateGETResponseBodyHamcrest(){
        given().
                baseUri("https://api.getpostman.com").
                header("X-Api-Key","PMAK-60bf1929270ce600348655cd-0bf95f57b43867a00df3157f88773843d4").
                when().
                get("/collections").
                then().
                assertThat().
                statusCode(200).
                //contains(): check all the elements in a collection & in a strict order
                //body("collections.name",contains("Postman API", "BOLD API's", "WhiteLabel API", "PushNotification", "Chat-Engine Api", "Whitelabel_Login", "Notifications API", "Co-WIN Public APIs"));
                //containsInAnyOrder(): check all the elements in a collection & in any order
                //body("collections.name",containsInAnyOrder("BOLD API's", "WhiteLabel API", "Postman API", "PushNotification", "Chat-Engine Api", "Whitelabel_Login", "Notifications API", "Co-WIN Public APIs"));
                //empty(): check if collection is empty
                 //body("collections.name",empty());
                //not(emptyArray()): check if the array is not empty
                //body("collections.name",is(not(empty())));
                //hasSize(): check size of a collection
                //body("collections.name",hasSize(8));
                //everyItem(startsWith()): check if every item in a collection starts with a specified string
                //body("collections.name",everyItem(startsWith("Api")));
                //hasKey() -> map -> check if map has specified key(value is not checked)
                //body("collections[0]",hasKey("id"));
                //hasValue()
                //body("collections[0]",hasValue("Postman API"));
                //hasEntry() -> map -> check if map has specified key value pair
                //body("collections[0]",hasEntry("id","04df713e-5991-4396-8765-d29afed12874"));
                //equalTo(Collections.EMPTY_MAP):  -> maps --> check if map empty
                //body("collections[0]",not(equalTo(Collections.EMPTY_MAP)));
                //allOf() -> matches if all matchers match
                body("collections[0].name",allOf(startsWith("Postman"),containsString("API")));

    }
}

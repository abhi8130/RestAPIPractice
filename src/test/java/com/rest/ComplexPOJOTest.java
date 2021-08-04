package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.collection.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONException;
import org.skyscreamer.jsonassert.*;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

public class ComplexPOJOTest {
    ResponseSpecification customResponseSpecification;
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
        customResponseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void complex_json_create_collection() throws JsonProcessingException, JSONException {
        Header myHeader = new Header("Content-Type","application/json");
        List<Header> myHeaderList = new ArrayList<Header>();
        myHeaderList.add(myHeader);

        Body myBody = new Body("raw","{\"data\": \"123\"}");

        RequestRequest myRequest = new RequestRequest("https://postman-echo.com/post","POST",
                myHeaderList,myBody,"This is a sample POST Request");

        RequestRootRequest myRequestRoot = new RequestRootRequest("Sample POST Request",myRequest);
        List<RequestRootRequest> requestRootList = new ArrayList<RequestRootRequest>();
        requestRootList.add(myRequestRoot);

        FolderRequest myFolder = new FolderRequest("This is a folder",requestRootList);
        List<FolderRequest> folderList = new ArrayList<FolderRequest>();
        folderList.add(myFolder);

        Info info = new Info("Sample Collection comple pojo","This is just a sample collection",
                "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        CollectionRequest myCollection = new CollectionRequest(info,folderList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(myCollection);

        String collection_uid = given().
                body(collectionRoot).
        when().
                post("/collections").
        then().spec(customResponseSpecification).
                extract().response().path("collection.uid");

        //deserialize
        CollectionRootResponse deserializedCollectionRootResponse = given().
                pathParam("collection_uid",collection_uid).
        when().
                get("collections/{collection_uid}").
        then().spec(customResponseSpecification).
                extract().
                response().
                as(CollectionRootResponse.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deSerializedCollectionRootStr = objectMapper.writeValueAsString(deserializedCollectionRootResponse);

        //assertions of full response json excluding 'url'
        JSONAssert.assertEquals(collectionRootStr, deSerializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                     new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
                        public boolean equal(Object o1, Object o2){
                             return true;
                    }
                })));
    }
}

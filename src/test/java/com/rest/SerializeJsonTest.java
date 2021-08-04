package com.rest;

import com.rest.pojo.mock.Address;
import com.rest.pojo.mock.Geo;
import com.rest.pojo.mock.UsersRoot;
import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SerializeJsonTest {
    ResponseSpecification customResponseSpecification;
    private Response response;

    @BeforeClass
    public void BeforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://jsonplaceholder.typicode.com").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(201).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void post_request_json_serialize(){
        Geo geo = new Geo("-37.3159","81.1496");
        Address address = new Address(geo,"Kulas Light","Apt. 55","Gwenborough","92998-3874");
        UsersRoot usersRoot = new UsersRoot(address,"Leanne Graham","Bret","Sincere@april.biz");

        Response response = given().
                body(usersRoot).
        when().
                post("/users").
        then().spec(customResponseSpecification).
                extract().
                response();
                assertThat(response.statusCode(),equalTo(201));
                assertThat(response.header("Content-Type"),equalTo("application/json; charset=utf-8"));
                //Refer: https://www.jvt.me/posts/2019/04/23/rest-assured-verify-field-not-set/
                // use Rest Assured's ValidatableResponse and Hamcrest's hasKey matcher: to validate the presence of the ID field.
                ValidatableResponse validatableResponse = response.then();
                validatableResponse.body("$", hasKey("id"));
    }
}

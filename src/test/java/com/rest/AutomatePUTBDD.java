package com.rest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
public class AutomatePUTBDD {
    RequestSpecification requestSpecification;
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
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validatePUTResponseBody(){
        String workspace_id = "341698e4-1f09-4eb3-b8fe-392187fb825e";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"New Workspace updated\",\n" +
                "        \"description\": \"Some description\"\n" +
                "        }\n" +
                "       \n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId",workspace_id).
        when().
                put("/workspaces/{workspaceId}").
        then().
                assertThat().
                log().all().
                body("workspace.name",equalTo("New Workspace updated"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspace_id));
    }
}

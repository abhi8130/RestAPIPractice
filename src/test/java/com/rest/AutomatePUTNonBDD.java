package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePUTNonBDD {
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
        String workspace_id = "6c69fea6-4582-4760-aaf8-51996e0a781a";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"New Workspace updated\",\n" +
                "        \"description\": \"Some description\"\n" +
                "        }\n" +
                "       \n" +
                "}";

        Response response = with().
                body(payload).
                pathParam("workspaceId", workspace_id).
                put("/workspaces/{workspaceId}");
        assertThat(response.<String>path("workspace.name"),equalTo("New Workspace updated"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.<String>path("workspace.id"),equalTo(workspace_id));
    }
}

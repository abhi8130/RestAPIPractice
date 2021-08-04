package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePOJO;
import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class WorkspacePOJOTest {
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
    public void workspace_serialize_deserialize(){
        Workspace workspace = new Workspace("MyWorkspace4","personal","description");
        HashMap<String,String> myHashmap = new HashMap<String, String>();
        workspace.setMyHashMap(myHashmap);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot deserializeWorkspaceRoot = given().
                body(workspaceRoot).
        when().
                post("/workspaces").
        then().spec(customResponseSpecification).
                extract().
                response().
                as(WorkspaceRoot.class);

        assertThat(deserializeWorkspaceRoot.getWorkspace().getName(),
                equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializeWorkspaceRoot.getWorkspace().getId(),
                matchesPattern("^[a-z0-9-]{36}$"));
    }

    @org.testng.annotations.Test(dataProvider = "workspace")
    public void workspace_parameterize_using_data_provider(String name,String type,String description){
        Workspace workspace = new Workspace(name,type,description);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot deserializeWorkspaceRoot = given().
                body(workspaceRoot).
                when().
                post("/workspaces").
                then().spec(customResponseSpecification).
                extract().
                response().
                as(WorkspaceRoot.class);

        assertThat(deserializeWorkspaceRoot.getWorkspace().getName(),
                equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializeWorkspaceRoot.getWorkspace().getId(),
                matchesPattern("^[a-z0-9-]{36}$"));
    }

    @DataProvider(name="workspace")
    public Object[][] getWorkspace(){
        return new Object[][]{{"myWorkspace5","personal","description1"},
        {"myWorkspace6","personal","description2"}};
    }
}

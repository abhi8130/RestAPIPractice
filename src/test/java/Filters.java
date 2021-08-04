import groovy.util.logging.Log;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItem;

public class Filters {

    @org.testng.annotations.Test
    public void loggingFilter(){
        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter(LogDetail.HEADERS)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS)).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void logToFile() throws FileNotFoundException {
        PrintStream outPutStream = new PrintStream(new File("logFile.log"));
        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter(LogDetail.HEADERS,outPutStream)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS,outPutStream)).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }
}

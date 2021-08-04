import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import static io.restassured.RestAssured.given;

public class ReuseFilter {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void BeforeClass() throws FileNotFoundException {
        PrintStream outPutStream = new PrintStream(new File("RestAssured.log"));

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                addFilter(new RequestLoggingFilter(outPutStream)).
                addFilter(new ResponseLoggingFilter(outPutStream));
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void reuseLoggingFilter(){
        given(requestSpecification).
                baseUri("https://postman-echo.com").
                //filter(new RequestLoggingFilter(LogDetail.HEADERS)).
                //filter(new ResponseLoggingFilter(LogDetail.STATUS)).
        when().
                get("/get").
        then().spec(responseSpecification).
                assertThat().
                statusCode(200);
    }
}

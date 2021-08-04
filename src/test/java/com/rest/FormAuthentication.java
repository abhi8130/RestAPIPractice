package com.rest;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FormAuthentication {

    @BeforeClass
    public void beforeClass(){
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setRelaxedHTTPSValidation().
                setBaseUri("https://localhost:8443").
                build();
    }

    @Test
    public void form_authentication_using_csrf_token(){
        //fetch session id using session filter
        SessionFilter filter = new SessionFilter();

        given().
                auth().form("dan", "dan123",
                new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                withAutoDetectionOfCsrf()).
                filter(filter).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                statusCode(200);
        System.out.println("Session Id: " + filter.getSessionId());

        //automate get profile
        given().
                sessionId(filter.getSessionId()).
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                statusCode(200).
        body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));

    }

    @Test
    public void form_authentication_sending_cookie(){
        //fetch session id using session filter
        SessionFilter filter = new SessionFilter();

        given().
                auth().form("dan", "dan123",
                new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                        withAutoDetectionOfCsrf()).
                filter(filter).
                log().all().
                when().
                get("/login").
                then().
                log().all().
                statusCode(200);
        System.out.println("Session Id: " + filter.getSessionId());

        //automate get profile
        given().
                cookie("JSESSIONID",filter.getSessionId()).
                log().all().
                when().
                get("/profile/index").
                then().
                log().all().
                statusCode(200).
                body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void form_authentication_sending_cookie_using_cookie_builder(){
        //fetch session id using session filter
        SessionFilter filter = new SessionFilter();

        given().
                auth().form("dan", "dan123",
                new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                        withAutoDetectionOfCsrf()).
                filter(filter).
                log().all().
                when().
                get("/login").
                then().
                log().all().
                statusCode(200);
        System.out.println("Session Id: " + filter.getSessionId());

        //Sending cookies using cookie builder
        Cookie cookie = new Cookie.Builder("JSESSIONID",filter.getSessionId()).
                setSecured(true).setHttpOnly(true).setComment("my cookie").build();

        //automate get profile
        given().
                cookie(cookie).
                log().all().
                when().
                get("/profile/index").
                then().
                log().all().
                statusCode(200).
                body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void sending_multiple_cookies(){
        //fetch session id using session filter
        SessionFilter filter = new SessionFilter();

        given().
                auth().form("dan", "dan123",
                new FormAuthConfig("/signin", "txtUsername", "txtPassword").
                        withAutoDetectionOfCsrf()).
                filter(filter).
                log().all().
                when().
                get("/login").
                then().
                log().all().
                statusCode(200);
        System.out.println("Session Id: " + filter.getSessionId());

        //Sending multiple cookies using cookie builder
        Cookie cookie = new Cookie.Builder("JSESSIONID",filter.getSessionId()).
                setSecured(true).setHttpOnly(true).setComment("myCookie").build();

        Cookie cookie1 = new Cookie.Builder("dummy","dummyValue").build();
        Cookies cookies = new Cookies(cookie,cookie1);

        //automate get profile
        given().
                cookies(cookies).
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                statusCode(200).
                body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void fetch_single_cookie(){
        Response response = given().
                log().all().
       when().
                get("/profile/index").
       then().
                log().all().
                extract().
                response();

        System.out.println(response.getCookie("JSESSIONID"));
        System.out.println(response.getDetailedCookie("JSESSIONID"));
    }


    @Test
    public void fetch_multiple_cookie(){
        Response response = given().
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                extract().
                response();

        Map<String,String> map = response.getCookies();

        for(Map.Entry<String,String> entry: map.entrySet()){
            System.out.println("cookie name = " + entry.getKey());
            System.out.println("cookie value = " + entry.getValue());
        }

        Cookies cookies1 = response.getDetailedCookies();
        List<Cookie> cookieList =  cookies1.asList();
        for(Cookie cookie: cookieList){
            System.out.println("cookie = " + cookie.toString());
        }
    }
}

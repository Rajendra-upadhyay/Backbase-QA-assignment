package com.backbase.restutils;

import com.backbase.constants.Endpoints;
import com.backbase.constants.JSONPayloadFilePath;
import com.backbase.pojo.User;
import com.backbase.reporting.ExtentReportUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static RequestSpecification requestSpecification;
    public static Response response;
    public static String pathParamSlug = "slug";
    public static String pathParamId="id";


    public static Response performPost(String endPoint, Object payload) {
        requestSpecification = getLoginRequestSpecification(payload);
        response = requestSpecification.post(endPoint);
        return response;
    }

    public static Response performPost(String endPoint, Object payload, String jwtToken) {
        requestSpecification = getPostRequestSpecification(payload, jwtToken);
        response = requestSpecification.post(endPoint);
        printRequestLogInReportForPutPost(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performPost(String endPoint, String pathParam, Object payload, String jwtToken) {
        requestSpecification = getRequestSpecification(pathParam, payload, jwtToken);
        response = requestSpecification.post(endPoint);
        printRequestLogInReportForPutPost(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performPostWithoutPayload(String endPoint, String pathParam, String jwtToken) {
        requestSpecification = getRequestSpecification(pathParam, jwtToken);
        requestSpecification.body("");
        response = requestSpecification.post(endPoint);
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performDelete(String endPoint, String slug, String id, String jwtToken) {
        requestSpecification = getDeleteRequestSpecification(slug, id, jwtToken);
        response = requestSpecification.delete(endPoint);
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performDelete(String endPoint, String slug, String jwtToken) {
        requestSpecification = getRequestSpecification(slug, jwtToken);
        response = requestSpecification.delete(endPoint);
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }
    public static Response performPut(String endPoint, String pathParam, Object payload, String jwtToken) {
        requestSpecification = getRequestSpecification(pathParam, payload, jwtToken);
        response = requestSpecification.put(endPoint);
        printRequestLogInReportForPutPost(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performGet(String endPoint, String pathParam, String jwtToken) {
        RequestSpecification requestSpecification = getRequestSpecification(pathParam, jwtToken);
        Response response = requestSpecification.get(endPoint);
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static RequestSpecification getRequestSpecification(String pathParam, Object payload, String jwtToken) {
        RestAssured.baseURI = Endpoints.BASE_URI;
        return RestAssured.given()
                .headers(getHeadersWithToken(jwtToken))
                .contentType(ContentType.JSON)
                .pathParam(pathParamSlug, pathParam)
                .body(payload);
    }

    public static RequestSpecification getRequestSpecification(String pathParam, String jwtToken) {
        RestAssured.baseURI = Endpoints.BASE_URI;
        return RestAssured.given()
                .headers(getHeadersWithToken(jwtToken))
                .pathParam(pathParamSlug, pathParam);
    }

    public static RequestSpecification getPostRequestSpecification(Object payload, String jwtToken) {
        RestAssured.baseURI = Endpoints.BASE_URI;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(getHeadersWithToken(jwtToken))
                .body(payload);
    }

    public static RequestSpecification getLoginRequestSpecification(Object payload) {
        RestAssured.baseURI = Endpoints.BASE_URI;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(getHeaders())
                .body(payload);
    }

    public static RequestSpecification getDeleteRequestSpecification(String slug, String id, String jwtToken) {
        RestAssured.baseURI = Endpoints.BASE_URI;
        return RestAssured.given()
                .headers(getHeadersWithToken(jwtToken))
                .contentType(ContentType.JSON)
                .pathParam(pathParamSlug, slug)
                .pathParam(pathParamId,id);
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        String authorization = "Basic Y2FuZGlkYXRleDpxYS1pcy1jb29s";
        String authority = "qa-task.backbasecloud.com";
        String accept = "application/json";

        headers.put("authorization", authorization);
        headers.put("authority", authority);
        headers.put("accept", accept);
        return headers;
    }

    public static Map<String, String> getHeadersWithToken(String jwtToken) {
        Map<String, String> headers = new HashMap<>(getHeaders());
        headers.put("jwtauthorization", jwtToken);
        return headers;
    }

    public static User getLoginPayload() {
        User userPayload;
        try {
            userPayload = objectMapper.readValue(new File(JSONPayloadFilePath.SIGNIN), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userPayload;
    }

    private static void printRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportUtil.logInfoDetails("URI is >>>"+queryableRequestSpecification.getURI());
        //ExtentReportUtil.logInfoDetails("Endpoint is: >>>" + queryableRequestSpecification.getBaseUri());
        ExtentReportUtil.logInfoDetails("Method is >>>" + queryableRequestSpecification.getMethod());
        ExtentReportUtil.logInfoDetails("Request headers are >>>");
        ExtentReportUtil.logHeaders(queryableRequestSpecification.getHeaders().asList());

    }

    private static void printRequestLogInReportForPutPost(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportUtil.logInfoDetails("URI is >>>"+queryableRequestSpecification.getURI());
        //ExtentReportUtil.logInfoDetails("Endpoint is: " + queryableRequestSpecification.getBaseUri());
        ExtentReportUtil.logInfoDetails("Method is >>>" + queryableRequestSpecification.getMethod());
        ExtentReportUtil.logInfoDetails("Request headers are >>>");
        ExtentReportUtil.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportUtil.logInfoDetails("Request body is >>>");
        ExtentReportUtil.logJson(queryableRequestSpecification.getBody());

    }

    private static void printResponseLogInReport(Response response) {
        ExtentReportUtil.logInfoDetails("Response status code is >>>" + response.getStatusCode());
        ExtentReportUtil.logInfoDetails("Response headers are >>>");
        ExtentReportUtil.logHeaders(response.getHeaders().asList());
        ExtentReportUtil.logInfoDetails("Response body is >>>");
        ExtentReportUtil.logJson(response.getBody().prettyPrint());
    }
}

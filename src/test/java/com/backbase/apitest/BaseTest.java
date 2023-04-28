package com.backbase.apitest;

import com.backbase.constants.Endpoints;
import com.backbase.pojo.User;
import com.backbase.restutils.RestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private static Logger logger = Logger.getLogger(BaseTest.class);
    static String jwtAuthorizationToken;
    static ObjectMapper objectMapper=new ObjectMapper();

    @BeforeSuite
    public void getJWTAuthorizationToken() {
        User payload = RestUtils.getLoginPayload();
        Response response = RestUtils.performPost(Endpoints.LOGIN_USER,payload);
        logger.info("get jwt authorization token with end point "+Endpoints.LOGIN_USER+" with payload "+"\n"+payload +" and response is>>> \n"+ response.prettyPrint());
        jwtAuthorizationToken = "Token "+response.jsonPath().get("user.token");
        logger.info("jwtauthorization token is >>> "+jwtAuthorizationToken);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("user.email"), payload.getUser().getEmail());
        logger.info("execution completed for getJWTAuthorizationToken >>>");
    }
}

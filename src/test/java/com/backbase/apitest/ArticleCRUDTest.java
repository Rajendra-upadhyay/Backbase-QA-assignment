package com.backbase.apitest;

import com.backbase.constants.Endpoints;
import com.backbase.constants.JSONPayloadFilePath;
import com.backbase.pojo.CreateArticle;
import com.backbase.restutils.RestUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class ArticleCRUDTest extends BaseTest {

    private static Logger logger = Logger.getLogger(ArticleCRUDTest.class);

    @Test(priority = 1, dataProvider = "getCreateArticlePayload")
    public void createNewArticle(CreateArticle createArticlePayload, ITestContext iTestContext) {

        Response response = RestUtils.performPost(Endpoints.CREATE_GET_ARTICLE, createArticlePayload, jwtAuthorizationToken);
        logger.info("create new article with end point " + Endpoints.CREATE_GET_ARTICLE + " with payload " + "\n" + createArticlePayload + " and response is >>>\n" + response.prettyPrint());

        String slug = response.jsonPath().getString("article.slug");
        iTestContext.getSuite().setAttribute("slug", slug);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("article.title"), createArticlePayload.getArticle().getTitle());

    }

    @Test(priority = 2)
    public void getArticle(ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performGet(Endpoints.GET_UPDATE_ARTICLE_BY_SLUG, slug, jwtAuthorizationToken);
        logger.info("get article with end point "+Endpoints.GET_UPDATE_ARTICLE_BY_SLUG+" with slug "+slug+" and response is >>>\n " + response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("article.slug"), slug);
    }

    @Test(priority = 3, dataProvider = "getUpdateArticlePayload")
    public void updateArticle(CreateArticle createArticlePayload, ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performPut(Endpoints.GET_UPDATE_ARTICLE_BY_SLUG, slug, createArticlePayload, jwtAuthorizationToken);
        logger.info("update article with end point "+Endpoints.GET_UPDATE_ARTICLE_BY_SLUG+" with slug "+slug+" and with payload "+createArticlePayload+" and response is >>> \n" + response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("article.title"), createArticlePayload.getArticle().getTitle());
    }

    @DataProvider(name = "getCreateArticlePayload")
    public Object[][] getCreateArticlePayload() {
        CreateArticle createArticle;
        try {
            createArticle = objectMapper.readValue(new File(JSONPayloadFilePath.CREATE_ARTICLE), CreateArticle.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Object[][]{{createArticle}};
    }

    @DataProvider(name = "getUpdateArticlePayload")
    public Object[][] getUpdateArticlePayload() {
        CreateArticle createArticle;
        try {
            createArticle = objectMapper.readValue(new File(JSONPayloadFilePath.UPDATE_ARTICLE), CreateArticle.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Object[][]{{createArticle}};
    }

    @BeforeMethod
    public void logExecutionStarted(Method method) {
        logger.info("===================================================");
        logger.info("execution started for " + method.getName() + ">>>");
    }

    @AfterMethod
    public void logExecutionCompleted(Method method) {
        logger.info("execution completed for " + method.getName() + ">>>");
        logger.info("=================***********========================");
    }
}

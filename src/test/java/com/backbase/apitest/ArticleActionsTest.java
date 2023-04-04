package com.backbase.apitest;

import com.backbase.constants.Endpoints;
import com.backbase.constants.JSONPayloadFilePath;
import com.backbase.pojo.Comment;
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

public class ArticleActionsTest extends BaseTest {

    private static Logger logger = Logger.getLogger(ArticleActionsTest.class);

    @Test(priority = 1, dataProvider = "getCommentPayload")
    public void addCommentOnArticle(Comment commentPayload, ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performPost(Endpoints.ADD_COMMENT, slug, commentPayload, jwtAuthorizationToken);
        logger.info("add comment on article with end point " + Endpoints.ADD_COMMENT + " with slug " + slug + " and with payload " + commentPayload + " and response is >>> \n" + response.prettyPrint());

        String commentId = response.jsonPath().getString("comment.id");
        iTestContext.getSuite().setAttribute("commentId", commentId);
        logger.info("comment id is >>>>>>> " + commentId);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("comment.body"), commentPayload.getComment().getBody());
    }

    //This test case is blocked, as comment is not getting saved on server
    // due to that reason we are not getting comment details from api so skipping this case
    @Test(priority = 2, enabled = false)
    public void getCommentOnArticleBySlug(ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performGet(Endpoints.ADD_COMMENT, slug, jwtAuthorizationToken);
        logger.info("getCommentOnArticleBySlug with end point " + Endpoints.ADD_COMMENT + " with slug " + slug + " and response is >>>\n " + response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(priority = 3, enabled = true)
    public void deleteComment(ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        String id = (String) iTestContext.getSuite().getAttribute("commentId");

        Response response = RestUtils.performDelete(Endpoints.DELETE_COMMENT, slug, id, jwtAuthorizationToken);
        logger.info("delete comment with end point " + Endpoints.DELETE_COMMENT + " with slug " + slug + " and id is " + id);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(priority = 4)
    public void markArticleFavorite(ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performPostWithoutPayload(Endpoints.FAVORITE_UNFAVORITE, slug, jwtAuthorizationToken);
        logger.info("favorite article with end point " + Endpoints.FAVORITE_UNFAVORITE + " with slug " + slug + " and response is >>>\n " + response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("article.slug"), slug);
    }

    @Test(priority = 5)
    public void markArticleUnFavorite(ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performDelete(Endpoints.FAVORITE_UNFAVORITE, slug, jwtAuthorizationToken);
        logger.info("unfavorite article with end point " + Endpoints.FAVORITE_UNFAVORITE + " with slug " + slug + " and response is >>> \n" + response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.jsonPath().getString("article.slug"), slug);
    }

    @Test(priority = 6)
    public void deleteArticle(ITestContext iTestContext) {
        String slug = (String) iTestContext.getSuite().getAttribute("slug");
        Response response = RestUtils.performDelete(Endpoints.DELETE_ARTICLE, slug, jwtAuthorizationToken);
        logger.info("delete article with end point " + Endpoints.DELETE_ARTICLE + " with slug " + slug);

        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_NO_CONTENT);
    }

    @DataProvider(name = "getCommentPayload")
    public Object[][] getCommentPayload() {
        Comment comment;
        try {
            comment = objectMapper.readValue(new File(JSONPayloadFilePath.ADD_COMMENT), Comment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Object[][]{{comment}};
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

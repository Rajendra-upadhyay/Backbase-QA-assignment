package com.backbase.webtest;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ArticleActions extends BasePage {

    private static Logger logger = Logger.getLogger(ArticleActions.class);


    @Test(priority = 1, enabled = true)
    public void addCommentOnArticle() {
        webDriver.findElement(By.partialLinkText(link_home)).click();
        webDriver.findElement(By.partialLinkText(link_global_feed)).click();
        webDriver.findElement(By.xpath(h1_updated_article_title)).click();
        webDriver.findElement(By.xpath(textarea_write_comment)).sendKeys(commentMessage);
        webDriver.findElement(By.xpath(button_post_comment)).click();
        String actualComment = webDriver.findElement(By.xpath(p_view_comment)).getText();
        logger.info("updated title value is >>>" + updatedArticleTitle);
        logger.info("actual comment message displayed as >>>" + actualComment);
        Assert.assertEquals(actualComment.trim(), commentMessage);
    }

    @Test(priority = 2, enabled = true)
    public void deleteCommentOnArticle() {
        boolean isCommentDisplayed;
        isCommentDisplayed = webDriver.findElement(By.xpath(p_view_comment)).isDisplayed();
        webDriver.findElement(By.xpath(span_delete_comment)).click();
        coolingPeriod(2);
        try {
            webDriver.findElement(By.xpath(p_view_comment)).isDisplayed();
        } catch (NoSuchElementException e) {
            isCommentDisplayed = false;
        }

        Assert.assertFalse(isCommentDisplayed);
    }

    @Test(priority = 3, enabled = true)
    public void favoriteArticle() {
        webDriver.findElement(By.partialLinkText(link_home)).click();
        webDriver.findElement(By.partialLinkText(link_global_feed)).click();
        webDriver.findElement(By.xpath(button_favorite)).click();
        coolingPeriod(2);
        String count = webDriver.findElement(By.xpath(button_favorite)).getText();
        logger.info("after favorite count " + count);
        Assert.assertTrue(Integer.valueOf(count) == 1);

    }

    @Test(priority = 4, enabled = true)
    public void UnFavoriteArticle() {
        webDriver.findElement(By.xpath(button_favorite)).click();
        coolingPeriod(2);
        String count = webDriver.findElement(By.xpath(button_favorite)).getText();
        logger.info("after unfavorite count " + count);
        Assert.assertTrue(Integer.valueOf(count) == 0);
    }

    @Test(priority = 5, enabled = true)
    public void deleteArticle() {
        boolean isArticleDeleted = false;
        webDriver.findElement(By.partialLinkText(link_home)).click();
        webDriver.findElement(By.partialLinkText(link_global_feed)).click();
        webDriver.findElement(By.xpath(h1_updated_article_title)).click();
        webDriver.findElement(By.xpath(button_delete_article)).click();
        webDriver.findElement(By.partialLinkText(link_global_feed)).click();
        coolingPeriod(1);
        try {
            webDriver.findElement(By.xpath(h1_updated_article_title)).isDisplayed();
        } catch (NoSuchElementException e) {
            isArticleDeleted = true;
        }
        Assert.assertTrue(isArticleDeleted);
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

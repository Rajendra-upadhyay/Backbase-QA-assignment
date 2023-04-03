package com.backbase.webtest;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ArticleCRUD extends BasePage {

    private static Logger logger = Logger.getLogger(ArticleCRUD.class);


    @Test(priority = 1)
    public void createNewArticle() {

        webDriver.findElement(By.partialLinkText(link_new_article)).click();
        Assert.assertTrue(webDriver.findElement(By.xpath(input_article_title)).isDisplayed());
        webDriver.findElement(By.xpath(input_article_title)).sendKeys(articleTitle);

        webDriver.findElement(By.xpath(input_article_about)).sendKeys(articleAbout);
        webDriver.findElement(By.xpath(input_article_description)).sendKeys(articleDescription);
        webDriver.findElement(By.xpath(input_tags)).sendKeys(tags);
        webDriver.findElement(By.xpath(button_publish_article)).click();

        Assert.assertEquals(webDriver.findElement(By.xpath(h1_article_title)).getText(), articleTitle);
        Assert.assertEquals(webDriver.findElement(By.xpath(div_article_description)).getText(), articleDescription);
    }

    @Test(priority = 2)
    public void getArticle() {
        webDriver.findElement(By.partialLinkText(link_home)).click();
        webDriver.findElement(By.partialLinkText(link_global_feed)).click();
        webDriver.findElement(By.xpath(h1_article_title)).click();
        Assert.assertEquals(webDriver.findElement(By.xpath(h1_article_title)).getText(), articleTitle);
    }

    @Test(priority = 3)
    public void updateArticle() {
        try {
            webDriver.findElement(By.xpath(link_edit_article)).click();
            webDriver.findElement(By.xpath(input_article_title)).clear();
            webDriver.findElement(By.xpath(input_article_title)).sendKeys(updatedArticleTitle);
            webDriver.findElement(By.xpath(button_publish_article)).click();
            coolingPeriod(2);
            Assert.assertEquals(webDriver.findElement(By.xpath(h1_updated_article_title)).getText(), updatedArticleTitle);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
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

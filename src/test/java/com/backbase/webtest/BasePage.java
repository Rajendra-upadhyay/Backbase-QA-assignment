package com.backbase.webtest;

import com.backbase.utils.Util;
import net.datafaker.Faker;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BasePage {

    private static Logger logger = Logger.getLogger(BasePage.class);

    static WebDriver webDriver;
    static Properties testData;
    //test data properties
    static String appURL;
    static String authenticateURL;
    static String email;
    static String password;
    static String articleTitle;
    static String articleAbout;
    static String articleDescription;
    static String updatedArticleTitle;
    static String tags;
    static String commentMessage;

    //sign in page objects xpath
    static String link_sign_in;
    static String input_email;
    static String input_password;
    static String button_sign_in;

    //home page objects
    static String link_home;
    static String link_new_article;
    static String link_global_feed;

    //create new article page object
    static String input_article_title;
    static String input_article_about;
    static String input_article_description;
    static String input_tags;
    static String button_publish_article;

    //view/edit/delete/comment article
    static String h1_article_title;
    static String h1_updated_article_title;
    static String div_article_description;
    static String link_edit_article;
    static String textarea_write_comment;
    static String button_post_comment;
    static String p_view_comment;
    static String span_delete_comment;
    static String button_favorite;
    static String button_delete_article;

    //settings page object
    static String link_settings;
    static String button_logout_user;

    @BeforeTest
    public void setUpDriverAndSignInUser() {
        logger.info("===================================================");
        logger.info("execution started for web test >>>");
        loadTestData();
        loadObjectXpath();
        System.out.println("article title value is "+articleTitle);
        System.out.println("article updated title value is "+updatedArticleTitle);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();

        webDriver.get(appURL);
        webDriver.get(authenticateURL);

        webDriver.findElement(By.xpath(link_sign_in)).click();
        webDriver.findElement(By.xpath(input_email)).sendKeys(email);
        webDriver.findElement(By.xpath(input_password)).sendKeys(password);
        webDriver.findElement(By.xpath(button_sign_in)).click();
    }

    @AfterTest
    public void logOutUserAndQuitDriver() {
        webDriver.findElement(By.xpath(link_settings)).click();
        webDriver.findElement(By.xpath(button_logout_user)).click();
        coolingPeriod(1);
        webDriver.quit();
        logger.info("execution completed for web test >>>");
        logger.info("===================================================");

    }

    public void loadTestData() {

        String fileName = "testdata.properties";
        testData = Util.loadPropertiesFile(fileName);
        appURL = testData.getProperty("appURL");
        authenticateURL = testData.getProperty("authenticateURL");
        email = testData.getProperty("email");
        password = testData.getProperty("password");
        //articleTitle = testData.getProperty("articleTitle");
        articleTitle = Util.generateRandomTitle();
        articleAbout = testData.getProperty("articleAbout");
        articleDescription = testData.getProperty("articleDescription");
        //updatedArticleTitle = testData.getProperty("updatedArticleTitle");
        updatedArticleTitle = articleTitle + " updated";
        tags = testData.getProperty("tags");
        commentMessage = testData.getProperty("commentMessage");
    }

    public void coolingPeriod(int timeInSeconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeInSeconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadObjectXpath() {

        //sign in page objects
        link_sign_in = "//a[@routerlink='/login']";
        input_email = "//input[@placeholder='Email']";
        input_password = "//input[@placeholder='Password']";
        button_sign_in = "//button[@type='submit']";

        //home page objects
        link_home = "Home";
        link_new_article = "New Article";
        link_global_feed = "Global Feed";

        //create new article page object
        input_article_title = "//input[@placeholder='Article Title']";
        input_article_about = "//*[contains(@placeholder,'this article about')]";
        input_article_description = "//*[contains(@placeholder,'Write your article')]";
        input_tags = "//input[@placeholder='Enter tags']";
        button_publish_article = "//button[text()=' Publish Article ']";

        //view/edit article details page objects
        h1_article_title = "//h1[text()='" + articleTitle + "']";
        h1_updated_article_title = "//h1[text()='" + updatedArticleTitle + "']";
        div_article_description = "//div[@class='col-md-12']/div/p";
        link_edit_article = "(//a[@class='btn btn-sm btn-outline-secondary'])[1]";
        textarea_write_comment = "//textarea[@placeholder='Write a comment...']";
        button_post_comment = "//button[text()=' Post Comment ']";
        p_view_comment = "//p[text()=' " + commentMessage + " ']";
        span_delete_comment = "//p[text()=' " + commentMessage + " ']//..//following-sibling::div/span[2]/i";
        button_favorite = "//h1[text()='" + updatedArticleTitle + "']//../..//div/app-favorite-button/button";
        button_delete_article = "(//button[text()=' Delete Article '])[1]";

        //setting page object
        link_settings = "//a[@routerlink='/settings']";
        button_logout_user = "//button[@class='btn btn-outline-danger']";

    }

}

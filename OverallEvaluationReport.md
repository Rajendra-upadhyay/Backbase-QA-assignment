
# Backbase QA Assigment Overall Evaluation Report




## Issues found

- User is able to create new article, without entering any value, there is no mandatory field check.
- When we click on Global Feed tab, it display list of article, now click on Favorite icon, it increase the count from 0 to 1 and it display on ui. Now reload the page and search this article again on Global Feed tab, it display favorite count is 0, it should display 1.
- On view article page, image is not loading on Publish Comment text area field
- When we write comment and click on Publish Comment button then comment display, now refresh or reload this page, getting error "{"errors":{"message":"Not Found","error":{"status":404}}}" 
- When we post comment on Article and then open article view page again by clicking on Global Feed tab, then added comment is not displaying on article, so comment is not getting save on db
- User is able to create new article with duplicate article details like, title. It should not allow duplicate title, there should be some validation. 

Note: These issues also observed during api testing, like when we add comment on article and then call api to get comment by slug, then it returns empty array, comment is not getting saved on server.


## Why you choose these test cases to automate

There are few reasons as:
- I have explored the app features and found Article CRUD and Article actions like post comment, delete comment, mark favorite/unfavorite is most important features from end user point of view.
- On every build/release, we have to make sure these are working as expected as a Regression test.
- So, I have decided first to automate these cases.


## Approach followed


- I have gone through the assignment documents and explore the web app and API's.
- Created API's collection in postman.
- Created manual series of test cases for Article CRUD, add/delete comment, mark favorite/unfavorite features.
- Then planned for Basic Automation framework creation which can be used for API and Web script creation, execution and reporting.
- Used language as JAVA and created maven project
- Added depepdencies for required libraries, plug in's to create basic automation framework for test script creation for API and Web.
- I have created framework and script for API for E2E Article CRUD and important article actions.
- Run script using apitestng.xml file and using command prompt/terminal as well.
- Then created script for web and run using webtestng.xml file and using  command prompt/terminal as well.

## Suggestions for improvement
For API:
- For update article, we can use patch method instead of put. As, if you need send or update only particular field then use patch method instead of put.

For Web:
- When we create new article there should be mandatory fields, like title and description and once user enters these field values then only Publish Article button should get enable on New Article page.
- In current scenario, when we click on New Article option, then New Article page open and Publish Article button is enable and when we click on it, it gets submit.
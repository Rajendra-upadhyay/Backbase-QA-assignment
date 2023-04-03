
# Backbase QA Assignment

This file provide information for below pointers:
- How to run test suite/cases related to API and Web
- How to access generated reports
- How to access manual test cases
- How to access postman collection to check API's request and response details.

### Precondition To Run Test Cases/Suite
 
- Please make sure JAVA [java 11 or later version] and Maven [any latest version] is installed on your machine
- JAVA_HOME and MAVEN_HOME path are set in environment variables

## Run Test Cases/Suite

Open terminal and clone the project from branch "qa-assignment-rajendra-upadhyay"

```bash
  git clone https://github.com/Rajendra-upadhyay/Backbase-QA-assignment.git
```

Go to the project directory Backbase-QA-assignment

```bash
  cd Backbase-QA-assignment
```
To run api test cases/suite

```bash
  mvn clean test -DsuiteXmlFile="apitestng.xml"
```
To run web test cases/suite

```bash
  mvn clean test -DsuiteXmlFile="webtestng.xml"
```
## Access Generated Test Reports

Go to project home directory and open reports folder, it contains test execution reports for API test and Web test as:

- APITestReport for API test with date time stamp
- WebTestReport for Web test with date time stamp

## Access Manual Test Cases

- Go to project home directory
- Navigate to src/test/resources/manualtestcases/ folder
- It contains file Backbase_QA_Assignment_Test_Cases.xlsx

## Access Postman Collection

- Go to project home directory
- Navigate to src/test/resources/manualtestcases/ folder
- It contains file backbase_collection.postman_collection.json

## Access Overall Evaluation Report

- Go to project home directory
- Open file OverallEvaluationReport.md
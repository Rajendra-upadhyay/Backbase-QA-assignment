package com.backbase.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class SetupAPIReport implements ITestListener {
    private ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();

    public void onStart(ITestContext context) {
    String fileName= "API"+ExtentReportUtil.getReportNameWithDateTimeStamp();
    String fullFileName=System.getProperty("user.dir")+"\\reports\\"+fileName;
    extentReports= ExtentReportUtil.createReportInstance(fullFileName,"E2E API Automation Report","E2E_API_Automation");
    }

    public void onFinish(ITestContext context) {
        if(extentReports !=null)
            extentReports.flush();
    }

    public void onTestStart(ITestResult result) {
    ExtentTest test=extentReports.createTest("Test Name: "+result.getTestClass().getName()+" "+result.getMethod().getMethodName());
    extentTest.set(test);

    }

    public void onTestFailure(ITestResult iTestResult) {
        ExtentReportUtil.logFailureDetails(iTestResult.getThrowable().getMessage());
        String stackTrace= Arrays.toString(iTestResult.getThrowable().getStackTrace());
        stackTrace=stackTrace.replaceAll(",","<br>");

        String formattedStackTrace="<details>\n" +
                "    <summary>Click here to see Exception Logs</summary>\n" +
                "    "+stackTrace+"\n" +
                "</details>";

        ExtentReportUtil.logStackTrace(formattedStackTrace);

    }
}


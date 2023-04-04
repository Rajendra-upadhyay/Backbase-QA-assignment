package com.backbase.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class SetupWebReport implements ITestListener {
    private ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();

    public void onStart(ITestContext context) {
    String fileName= "Web"+ExtentReportUtil.getReportNameWithDateTimeStamp();
    String fullFileName=System.getProperty("user.dir")+"\\reports\\"+fileName;
    extentReports= ExtentReportUtil.createReportInstance(fullFileName,"Web_Automation_Test_Report","Web_Automation_Test_Results");
    }

    public void onFinish(ITestContext context) {
        if(extentReports !=null)
            extentReports.flush();
    }

    public void onTestStart(ITestResult result) {
    ExtentTest test=extentReports.createTest("Test Name: "+result.getTestClass().getName()+" "+result.getMethod().getMethodName());
    extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        extentTest.get().pass(MarkupHelper.createLabel(iTestResult.getTestName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        extentTest.get().skip(MarkupHelper.createLabel(iTestResult.getTestName(),ExtentColor.GREY));
    }

    public void onTestFailure(ITestResult iTestResult) {
        extentTest.get().fail(MarkupHelper.createLabel(iTestResult.getThrowable().getMessage(), ExtentColor.RED));
        String stackTrace= Arrays.toString(iTestResult.getThrowable().getStackTrace());
        stackTrace=stackTrace.replaceAll(",","<br>");
        String formattedStackTrace="<details>\n" +
                "    <summary>Click here to see Exception Logs</summary>\n" +
                "    "+stackTrace+"\n" +
                "</details>";

        extentTest.get().fail(formattedStackTrace);
    }
}

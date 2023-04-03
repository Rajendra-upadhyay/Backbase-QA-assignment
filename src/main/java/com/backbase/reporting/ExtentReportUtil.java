package com.backbase.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.http.Header;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExtentReportUtil {

    public static ExtentReports extentReports;
    public static ExtentReports createReportInstance(String fileName, String reportName,String reportTitle){
        ExtentSparkReporter extentSparkReporter=new ExtentSparkReporter(fileName);
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setReportName(reportName);
        extentSparkReporter.config().setDocumentTitle(reportTitle);
        extentSparkReporter.config().setEncoding("utf-8");

        extentReports=new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        return extentReports;
    }

    public static String getReportNameWithDateTimeStamp(){
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime=LocalDateTime.now();
        String formattedTime=dateTimeFormatter.format(localDateTime);
        return "TestReport"+formattedTime+".html";
    }

    public static void logPassDetails(String log){
        SetupAPIReport.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
    }

    public static void logFailureDetails(String log){
        SetupAPIReport.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
    }

    public static void logStackTrace(String log){
        SetupAPIReport.extentTest.get().fail(log);
    }

    public static void logInfoDetails(String log){
        SetupAPIReport.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.GREY));
    }

    public static void logJson(String log){
        SetupAPIReport.extentTest.get().info(MarkupHelper.createCodeBlock(log,CodeLanguage.JSON));
    }


    public static void logHeaders(List<Header> headerList){
        String[][] arrayHeaders=headerList.stream().map(header -> new String[]{header.getName(),header.getValue()})
                .toArray(String[][]::new);
        SetupAPIReport.extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
    }

    public static void logWarningDetails(String log){
        SetupAPIReport.extentTest.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
    }
}

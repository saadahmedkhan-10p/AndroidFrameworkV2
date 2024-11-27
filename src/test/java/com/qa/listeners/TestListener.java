package com.qa.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import com.qa.reports.ExtentReport;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TestListener implements ITestListener {
    public void onTestFailure(ITestResult result){
        if(result.getThrowable() != null){
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            result.getThrowable().printStackTrace(printWriter);
            System.out.println(stringWriter.toString());
        }
        File file = ((TakesScreenshot) AppDriver.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        }catch (IOException exception){
            exception.printStackTrace();
        }

        Map<String, String> params = new HashMap<>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "screenshots" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + File.separator + AppFactory.getDateAndTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(file, new File(imagePath));
            Reporter.log("This is the sample screenshot");
            Reporter.log("<a href= '" + completeImagePath + "'> <img src='" + completeImagePath + "' height='100' width='100' /> </a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExtentReport.getTest().fail("Test Fail",
                MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath)
                        .build());
        assert encoded != null;
        ExtentReport.getTest().fail("Test Fail",
                MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII))
                        .build());
        ExtentReport.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestStart(ITestResult result){
        ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                .assignCategory(AppDriver.getPlatformName() + "-" + AppDriver.getDeviceName())
                .assignAuthor("10Pearls");
    }

    @Override
    public void onTestSuccess(ITestResult result){
        ExtentReport.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result){
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result){
        // TODO: Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context){
        // TODO: Auto-generated method stub
    }

    @Override
    public void onFinish(ITestContext context){
        ExtentReport.getExtentReports().flush();
    }
}

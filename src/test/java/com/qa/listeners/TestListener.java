package com.qa.listeners;

import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestListener implements ITestListener {
    public void onTestFailure(ITestResult result){
        if(result.getThrowable() != null){
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            result.getThrowable().printStackTrace(printWriter);
            System.out.println(stringWriter.toString());
        }
    }
}

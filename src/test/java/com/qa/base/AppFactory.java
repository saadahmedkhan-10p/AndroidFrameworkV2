package com.qa.base;

import com.qa.utils.Utilities;
import com.qa.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppFactory {

    public static AppiumDriver driver;
    public static ConfigReader configReader;

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws MalformedURLException, FileNotFoundException {
        try{
            configReader = new ConfigReader();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("appium:platformVersion", platformVersion);
            capabilities.setCapability("appium:deviceName", deviceName);
            capabilities.setCapability("appium:appPackage", configReader.getAppPackage());
            capabilities.setCapability("appium:appActivity", configReader.getAppActivity());
            capabilities.setCapability("appium:newCommandTimeout", configReader.getCommandTimeoutValue());
            capabilities.setCapability("appium:automationName", configReader.getAutomationName());
            capabilities.setCapability("appium:app", System.getProperty("user.dir") + configReader.getApkPath());
            capabilities.setCapability("appium:noReset", configReader.getNoReset());
            driver = new AndroidDriver(new URL(configReader.getAppiumServerEndpointURL()), capabilities);
            AppDriver.setDriver(driver);
            System.out.println("Android driver is set");
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public void waitForElement(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utilities.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void clickElement(WebElement element){
        waitForElement(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text){
        waitForElement(element);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute){
        waitForElement(element);
        return element.getAttribute(attribute);
    }

    @AfterTest
    public static void quitDriver(){
        if(null != driver){
            driver.quit();
        }
    }
}

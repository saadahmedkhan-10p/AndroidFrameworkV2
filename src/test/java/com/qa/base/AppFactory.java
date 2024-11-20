package com.qa.base;

import com.qa.utils.Utilities;
import com.qa.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class AppFactory {

    public static AppiumDriver driver;
    public static ConfigReader configReader;
    protected static HashMap<String, String> stringHashMap = new HashMap<>();
    InputStream stringIs;
    Utilities utilities;
    static Logger log = LogManager.getLogger(AppFactory.class.getName());

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws IOException, ParserConfigurationException, SAXException {
        try{
            log.debug("This is debug message");
            log.info("This is info message");
            log.warn("This is warning message");
            log.error("This is error message");
            log.fatal("This is fatal message");

            utilities = new Utilities();
            configReader = new ConfigReader();
            String xmlFileName = "strings/strings.xml";
            stringIs = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            stringHashMap = utilities.parseStringXML(stringIs);

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
        } finally {
            if (stringIs != null){
                stringIs.close();
            }
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

package base;

import configurationFileReader.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

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

    @AfterTest
    public static void quitDriver(){
        if(null != driver){
            driver.quit();
        }
    }
}

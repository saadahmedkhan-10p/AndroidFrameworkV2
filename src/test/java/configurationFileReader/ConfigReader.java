package configurationFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader() throws FileNotFoundException {
        //this.properties = properties;
        BufferedReader reader;
        String propertyFilePath = "configuration/Config.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();

            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getAutomationName(){
        String automationName = properties.getProperty("automationName");
        if (automationName != null) return automationName;
        else throw new RuntimeException("automationName not specified in the Configuration.properties");
    }

    public String getPlatformName(){
        String platformName = properties.getProperty("platformName");
        if (platformName != null) return platformName;
        else throw new RuntimeException("platformName not specified in the Configuration.properties");
    }

    public String getAppPackage(){
        String appPackage = properties.getProperty("appPackage");
        if (appPackage != null) return appPackage;
        else throw new RuntimeException("appPackage not specified in the Configuration.properties");
    }

    public String getAppActivity(){
        String appActivity = properties.getProperty("appActivity");
        if (appActivity != null) return appActivity;
        else throw new RuntimeException("appActivity not specified in the Configuration.properties");
    }

    public String getCommandTimeoutValue(){
        String commandTimeoutValue = properties.getProperty("commandTimeoutValue");
        if (commandTimeoutValue != null) return commandTimeoutValue;
        else throw new RuntimeException("commandTimeoutValue not specified in Configuration.properties");
    }

    public String getApkPath(){
        String apkPath = properties.getProperty("apkPath");
        if (apkPath != null) return apkPath;
        else throw new RuntimeException("apkPath not specified in Configuration.properties");
    }

    public String getNoReset(){
        String noReset = properties.getProperty("noReset");
        if (noReset != null) return noReset;
        else throw new RuntimeException("noReset not specified in Configuration.properties");
    }

    public String getAppiumServerEndpointURL(){
        String appiumServerEndpointURL = properties.getProperty("appiumServerEndpointURL");
        if (appiumServerEndpointURL != null) return appiumServerEndpointURL;
        else throw new RuntimeException("appiumServerEndpointURL not specified in Configuration.properties");
    }
}

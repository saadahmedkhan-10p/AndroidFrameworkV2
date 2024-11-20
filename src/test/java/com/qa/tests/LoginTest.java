package com.qa.tests;

import com.qa.base.AppFactory;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class LoginTest extends AppFactory {
    LoginPage loginPage;
    ProductPage productPage;
    JSONObject loginUser;
    InputStream inputStream;

    @BeforeClass
    public void setupDataStream() throws IOException {
        try {
        String dataFileName = "data/loginUsers.json";
        inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
        JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
        loginUser = new JSONObject(jsonTokener);
    } catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            {
                if (inputStream != null){
                    inputStream.close();
                }
            }
        }
        }

    @BeforeMethod
    public void setup(Method method){
        loginPage = new LoginPage();
        System.out.println("\n" + "********** Starting Test: " + method.getName() +" **********" + "\n");
       // productPage = new ProductPage(); /* no need for this as this was returned in click method*/ helps with memory consumption
    }

    @Test
    public void verifyInvalidCreds(){
        System.out.println("This test is used to verify invalid creds and get error message");
        loginPage.enterValidUserName(loginUser.getJSONObject("invalidUser").getString("username"));
        loginPage.enterPassword(loginUser.getJSONObject("invalidUser").getString("password"));
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_UserName");

        System.out.println("Actual Error Message: " +actualErrorMessage+ "\n" + "Expected Error Message: " +expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
    @Test
    public void verifyValidUserLogin() throws InterruptedException {
        System.out.println("This test is used to login with valid creds");
        loginPage.enterValidUserName(loginUser.getJSONObject("validUser").getString("username"));
        loginPage.enterPassword(loginUser.getJSONObject("validUser").getString("password"));
        productPage = loginPage.clickLoginButton();
        System.out.println("Login successful");

        String actualProductTitle = productPage.getProductHeaderText();
        String expectedProductTitle = stringHashMap.get("product_Title");

        System.out.println("Actual Product Title: " +actualProductTitle+ "\n" + "Expected Title: " + expectedProductTitle);

        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }
}

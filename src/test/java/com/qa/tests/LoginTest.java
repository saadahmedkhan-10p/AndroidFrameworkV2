package com.qa.tests;

import com.qa.base.AppFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;

import java.lang.reflect.Method;

public class LoginTest extends AppFactory {
    LoginPage loginPage;
    ProductPage productPage;

    @BeforeMethod
    public void setup(Method method){
        loginPage = new LoginPage();
        System.out.println("\n" + ""********** Starting Test: " + method.getName() +" **********" + "\n");
       // productPage = new ProductPage(); /* no need for this as this was returned in click method*/ helps with memory consumption
    }

    @Test
    public void verifyInvalidCreds(){
        System.out.println("This test is used to verify invalid creds and get error message");
        loginPage.enterValidUserName("jkjkjk");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Username and password do not match any user in this service.";

        System.out.println("Actual Error Message: " +actualErrorMessage+ "\n" + "Expected Error Message: " +expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
    @Test
    public void verifyValidUserLogin() throws InterruptedException {
        System.out.println("This test is used to login with valid creds");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        productPage = loginPage.clickLoginButton();
        System.out.println("Login successful");

        String actualProductTitle = productPage.getProductHeaderText();
        String expectedProductTitle = "PRODUCTS";

        System.out.println("Actual Product Title: " +actualProductTitle+ "\n" + "Expected Title: " + expectedProductTitle);

        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }
}

package testCases;

import base.AppFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends AppFactory {
    LoginPage loginPage;

    @BeforeMethod
    public void setup(){
        loginPage = new LoginPage();
    }

    @Test
    public void verifyValidUserLogin() throws InterruptedException {
        System.out.println("This test is used to login with valid creds");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        System.out.println("Login successful");
    }

    @AfterMethod
    public void tearDown(){
        AppFactory.quitDriver();
    }
}

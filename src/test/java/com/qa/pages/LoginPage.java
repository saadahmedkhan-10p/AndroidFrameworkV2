package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends AppFactory {
    public LoginPage(){
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()),this);
    }

    @AndroidFindBy(accessibility = "test-Username")
    public WebElement userNameTextBox;

    @AndroidFindBy(accessibility = "test-Password")
    public WebElement passwordTextBox;

    @AndroidFindBy(accessibility = "test-LOGIN")
    public WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    public WebElement error;

    public void enterValidUserName(String userName){
        sendKeys(userNameTextBox, userName, "Username is " + userName);
    }

    public void enterPassword(String password){
        sendKeys(passwordTextBox, password, "Password is " + password);
    }

    public ProductPage clickLoginButton(){
        clickElement(loginButton, "Clicking on Login Button");
        return new ProductPage();
        //when a click redirects to a new page - return that page
    }

//    public String getErrorMessage(){
//        return getAttribute(error, "text");
//    }

    public String getErrorMessage(){
        return getText(error, "Error text is: ");
    }
}

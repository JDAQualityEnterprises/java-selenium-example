package com.jda.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {

    public HomePage() {}
    public HomePage(WebDriver driver) { super(driver); }

    @FindBy(xpath="//a[contains(@href, 'mailto')]")
    public WebElement emailLink;
}

package com.jda.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactsPage extends PageBase {

    public ContactsPage() {}
    public ContactsPage(WebDriver driver) { super(driver); }

    @FindBy(xpath="//a[contains(@href, 'mailto')]")
    public WebElement emailLink;

    @FindBy(xpath="(//h1)[1]")
    public WebElement heading;
}

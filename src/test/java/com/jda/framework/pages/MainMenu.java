package com.jda.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainMenu extends PageBase {

    public MainMenu() {}
    public MainMenu(WebDriver driver) { super(driver); }

    @FindBy(xpath="//nav[contains(@style, 'visible')]/ul/li[contains(.,'Home')]//a")
    public WebElement homeLink;

    @FindBy(xpath="//nav[contains(@style, 'visible')]/ul/li[contains(.,'Contact')]//a")
    public WebElement contactLink;

    @FindBy(xpath="//nav[contains(@style, 'visible')]/ul/li[contains(.,'About')]//a")
    public WebElement aboutLink;

    @FindBy(xpath="//nav[contains(@style, 'visible')]/ul/li[contains(.,'Clients')]//a")
    public WebElement clientsLink;
}

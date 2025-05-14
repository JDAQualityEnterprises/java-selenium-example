package com.jda.framework.pages;

import org.apache.commons.io.FileUtils;
import com.jda.core.BrowserDriver;
import com.jda.core.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


public abstract class PageBase {

    protected String url;

    public String siteUrl = Config.getSetting("SiteUrl");

    protected WebDriver driver = BrowserDriver.getDriver();

    protected String pageTitle;

    protected static long pollTime = 500;
    protected static long timeout =  10;

    public PageBase()
    {}

    public PageBase(WebDriver driver){

        this.driver = driver;
        //org.openqa.selenium.support.PageFactory.initElements(driver, this);
    }


    public WebElement lnk_NavItems(String labelName){
        return driver.findElement(By.xpath(String.format(".//*[@id='%s']", labelName)));
    }

    public WebElement lnk_SysFunctionsByLinkName(String functionGroupName){
        return driver.findElement(By.xpath(String.format("//a[@id='%s']", functionGroupName)));
    }

    public WebElement lnk_SysFunctionsByPartialLinkName(String functionGroupName){
        return driver.findElement(By.xpath(String.format("//a[contains(.,'%s')]", functionGroupName)));
    }

    public WebElement lnk_elementById(String elementId){
        String xpath = String.format(".//*[@id='%s']", elementId.toLowerCase().replace(" ", "-"));
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement lnk_MenuItemByPartialLinkName(String functionGroupName){
        return driver.findElement(By.xpath(String.format("//label[contains(.,'%s')]", functionGroupName)));
    }

    public WebElement lbl_pageLabelByName(String label){
        return driver.findElement(By.xpath(String.format("//nav[@id='vd-heading']//div[contains(.,'%s')]", label)));
    }

    public WebElement lnk_roleByLinkName(String rolename){
        return driver.findElement(By.xpath(String.format("//td[@title='%s']", rolename)));
    }

    public void open(String pageurl){
        driver.navigate().to(pageurl);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getURL() {
        return url;
    }

    //Webdriver Wrappers
    protected void sendText(String cssSelector, String text) {
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(text);
    }

    /** Is the text present in page. */
    public boolean isTextPresentOnPage(String text){
        try {
            driver.getPageSource().contains(text);
        }
       catch (WebDriverException e)
        {
            e.printStackTrace();
        }
        return driver.getPageSource().contains(text);
    }

    public boolean isTextPresent(By by, String text){
        return driver.findElement(by).getText().contains(text);
    }

    /** Is the Element in page. */
    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);//if it does not find the element throw NoSuchElementException, thus returns false.
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(String _cssSelector){
        try {
            driver.findElement(By.cssSelector(_cssSelector));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public boolean isElementPresentAndDisplayed(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebElement getWebElement(By by){
        return driver.findElement(by);
    }


    public void WaitforSecs(int secs)
    {
        try {

            Thread.sleep(secs*1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //
    public boolean isElementVisible(WebElement ele)
    {
        boolean eleNotPresent;

        try {
            ele.isDisplayed();
            eleNotPresent=true;
        }
        catch (NoSuchElementException | ElementNotInteractableException e){
            eleNotPresent = false;
        }

        return eleNotPresent;
    }

    public void WaitBy(By by)
    {
        //Wait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Config.getSetting("Timeout"))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

    }

    public void WaitForElement(WebElement ele, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    public void NewSecCheckAndContinue(WebElement ele, int timeout)
    {
        String ExpCertText = "There is a problem with this website";

        driver.findElement(By.id("overridelink"));
    }

    public void SaveScreenshot(String screenShotFolder)
    {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(screenShotFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forceClick(WebElement ele)
    {
        new Actions(driver).moveToElement(ele).build().perform();
        ele.sendKeys(Keys.ENTER);
        WaitforSecs(2);

        try {
            ele.click();
        }
        catch (WebDriverException e){}
    }

    public void clickJS(WebElement ele, WebDriver driver)
    {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", ele);
        }
        catch (JavascriptException e)
        {
            e.printStackTrace();
        }
    }


    //COOKIES REGION

    protected void DeleteCookie(String name)
    {
        driver.manage().deleteCookieNamed(name);
    }

    protected  void SetCookie(String name, String value)
    {
        Cookie newCookie = new Cookie(name, value);
        driver.manage().addCookie(newCookie);
    }

    protected String GetCookieValue(String name)
    {
        Cookie cookie = driver.manage().getCookieNamed(name);
        if (cookie != null)
            return cookie.getValue();
        return null;
    }
}

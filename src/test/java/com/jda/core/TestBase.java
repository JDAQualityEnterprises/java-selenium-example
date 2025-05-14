package com.jda.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class TestBase {
    protected WebDriver driver;

    public TestBase ()
    {
        driver = BrowserDriver.setDriver(driver, Config.getSetting("Browser"));
    }

    @BeforeEach
    public void setup() {
        Support.openURL(BrowserDriver.getDriver(), Config.getSetting("SiteUrl"));
    }

    @AfterEach
    public void teardown() {
        if (BrowserDriver.getDriver() != null) {
            BrowserDriver.getDriver().quit();
        }
    }
}

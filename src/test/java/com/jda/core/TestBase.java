package com.jda.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public class TestBase {
    private static final Logger log = LogManager.getLogger(TestBase.class);
    private static ExtentTest TestLevel;
    protected static WebDriver driver;
    protected static ExtentReports Reporter;
    protected static ThreadLocal<ExtentTest> TestReporter = new ThreadLocal<>();

    public TestBase ()
    {
    }

    @BeforeAll
    public static void setupAll(TestInfo info) {

        Reporter = ExtentReporter.getExtentReports();
        TestLevel = Reporter.createTest(info.getDisplayName());
        TestReporter.set(TestLevel);
    }

    @BeforeEach
    public void setup(TestInfo info) {
        String url = Config.getSetting("SiteUrl");
        String testName = StringHelper.methodNameFromTestInfo(info);

        TestReporter.set(TestLevel.createNode(testName));

        driver = BrowserDriver.setDriver(driver, Config.getSetting("Browser"));

        TestReporter.get().log(Status.INFO, "JDA go to URL " + url);
        Support.openURL(BrowserDriver.getDriver(), Config.getSetting("SiteUrl"));
    }

    @AfterEach
    public void teardown() {
        if (BrowserDriver.getDriver() != null) {
            BrowserDriver.getDriver().quit();
        }
    }
}

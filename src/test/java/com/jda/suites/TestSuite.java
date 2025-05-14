package com.jda.suites;

import com.aventstack.extentreports.ExtentReports;
import com.jda.core.Config;
import com.jda.core.ExtentReporter;
import com.jda.tests.ContactsTests;
import com.jda.tests.HomeTests;
import org.junit.platform.suite.api.*;

@Suite
@SelectClasses({ContactsTests.class, HomeTests.class})
@SuiteDisplayName("Jda Test Suite")
public class TestSuite {
    protected static ExtentReports Reporter = null;

    @BeforeSuite
    public static void setup()
    {
        System.out.println("TestSuite.setup");
        if (Reporter == null) {
            Reporter = ExtentReporter.setExtentReports(Reporter, Config.getSetting("ReportTemplate"));
        }
        System.out.println("MyOwnListener.testPlanExecutionStarted");
    }

    @AfterSuite
    public static void teardown() {
        Reporter.flush();
    }
}

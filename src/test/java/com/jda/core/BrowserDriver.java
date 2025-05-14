package com.jda.core;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.*;

import org.assertj.core.api.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.jda.core.TestBase.TestReporter;


public class BrowserDriver {
	
	private static WebDriver driver = null;
	private static final Logger logger = LogManager.getLogger("Driver");

	public static WebDriver getDriver() {
		return driver;
	}

	public static WebDriver setDriver(WebDriver driver, String BrowserType) {
		String configHeadless = Config.getSetting("headless");
		boolean headless = configHeadless.isEmpty() || Boolean.parseBoolean(configHeadless);

		String Message;
		try{
			switch (BrowserType) {
				case "Edge":
					EdgeOptions options = new EdgeOptions();
					driver = new EdgeDriver(options);
					break;
				case "FF":
					driver = new FirefoxDriver();
					break;
				case "CH":
					ChromeOptions opts = new ChromeOptions();

					if (headless)
					{
						opts.addArguments("--headless=new");
					}

					driver = new ChromeDriver(opts);

					Capabilities capabilitiesCH = ((RemoteWebDriver) driver).getCapabilities();

					//Output environment variables
					Message = "BrowserDriver.setDriver.Platform: " + capabilitiesCH.getPlatformName();
					TestReporter.get().log(Status.INFO,Message);
					logger.info(Message);
					Message = "BrowserDriver.setDriver.Browser: " + capabilitiesCH.getBrowserName();
					TestReporter.get().log(Status.INFO,Message);
					logger.info(Message);
					Message = "BrowserDriver.setDriver.Version: " + capabilitiesCH.getBrowserVersion();
					TestReporter.get().log(Status.INFO,Message);
					logger.info(Message);

					driver.manage().window().maximize();
				default:
					break;
				}
			
			return BrowserDriver.driver = driver;
		}
		catch (Exception e) {
			Message = "BrowserDriver.setDriver.Error";

			//Logging.captureScreenshot(driver, Driver.screenShotsFolder, "setDriver");
			logger.fatal(Message + ": " + e);
			Assertions.fail(Message + ": " + e);
			return null;
		}
	}

}

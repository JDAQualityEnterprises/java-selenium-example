package com.jda.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.fail;

public class ExtentReporter {
	private static Logger logger = LogManager.getLogger("Hook");
	private static ExtentReports extent = null;
	//private static ExtentTest test;
	private static ExtentSparkReporter htmlReporter;
	private static String filePath = "./extentreport.html";
	
	public static final ExtentReports getExtentReports() {
		try{
			logger.debug("ExtentReporter | getExtentReports | Return the Extent Reports object");
			return extent;
		}
		catch (Exception e) {
			fail("ExtentReporter | getExtentReports | Error:", e);
			logger.error("ExtentReporter | getExtentReports | Error:", e);
			return null;
		}
	}

	
	public static final ExtentReports setExtentReports(ExtentReports extent, String ConfigFile){
		try{
			logger.debug("ExtentReporter | setExtentReports | Create the Extent Reports object");
			extent = new ExtentReports();
			
			extent.attachReporter(getHtmlReporter(ConfigFile));
			return ExtentReporter.extent = extent;
			//return extent;
		}
		catch (Exception e) {
			fail("ExtentReporter | setExtentReports | Error:", e);
			logger.error("ExtentReporter | setExtentReports | Error:", e);
			return null;
		}
	}
 
	private static ExtentSparkReporter getHtmlReporter(String ConfigFile) {
		try{
			logger.debug("ExtentReporter | getHtmlReporter | ConfigFile: " + ConfigFile);
			
			//Set the filepath of the Extent html report
	        htmlReporter = new ExtentSparkReporter(filePath);
			
	        //htmlReporter.loadXMLConfig("./Extent_Regression_Config.xml");
			htmlReporter.loadXMLConfig("src/test/resources/" + ConfigFile);

	        //Append to existing report 
	        //htmlReporter.setAppendExisting(true);
	        // make the charts visible on report open
	        //htmlReporter.config().setChartVisibilityOnOpen(true);
			
	        //htmlReporter.config().setDocumentTitle("HRE automation report");
	        //htmlReporter.config().setReportName("Regression cycle");
	        return htmlReporter;
		}
		catch (Exception e) {
			fail("ExtentReporter | getHtmlReporter | Error:", e);
			logger.error("ExtentReporter | getHtmlReporter | Error:", e);
			return null;
		}
	}
}

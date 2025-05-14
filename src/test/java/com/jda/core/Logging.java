package com.jda.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Logging {
	
	private static Logger logger = LogManager.getLogger("Driver");
	private static String logfile = null;
	private static String screenshotfile = null;
	private static String elementscreenshotfile = null;
	
	public static final String getLogFile(){
		return logfile;	
	}
	
	public static final String createLogFile(String topLevelFolder, String featureName){
	    String logFile = topLevelFolder.concat("\\").concat(featureName).concat(".txt");
        //File file = new File(logFile);
        //if(Files.exists(file.toPath())) {
        return logFile;


        /*
        try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss");
			Date date = new Date();
			String sCurrentDateTime = dateFormat.format(date);
			logfile = topLevelFolder + "\\Log_" + sCurrentDateTime + ".txt";
			return Logging.logfile;
		}
		catch (Exception e) {
			Assert.fail("Logging | createLogFile | Error:", e);
			logger.error("Logging | createLogFile | Error:", e);
			return null;
		}
		*/
	}
	
	public static final String getScreenshot(){
		return screenshotfile;
	}
	
	public static final String captureScreenshot(WebDriver driver, String TopLeveLDir, String Title){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss");
		Date date = new Date();
		String sCurrentDateTime = dateFormat.format(date);
		String Message;
		
		String sFileName = TopLeveLDir + "\\" + Title + "_" + sCurrentDateTime +".png";
		try {
			//Take screenshot and store as a file format
			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
			//now copy the  screenshot to desired location using copyFile method
			FileUtils.copyFile(screenshotFile, new File(sFileName));
		 
			Message = "Logging | captureScreenshot | Screenshot file created successfully: " + sFileName;
			logger.info(Message);
			//Reporter.log(Message);
			return Logging.screenshotfile = sFileName;
		} 
		catch (IOException e){
			Assertions.fail("Logging | captureScreenshot | Error:", e);
			logger.error("Logging | captureScreenshot | Error:", e);
			return null;
		}
	}
	
	public static final String getElementScreenshot(){
		return elementscreenshotfile;
	}
	
	public static final String captureElementScreenshot(WebDriver driver, WebElement element, String TopLeveLDir, String Title){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss");
		Date date = new Date();
		String sCurrentDateTime = dateFormat.format(date);
		String Message;
		
		String sFileName = TopLeveLDir + "\\" + Title + "_" + sCurrentDateTime +".png";
		try {
			//Take screenshot of whole screen
			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
			//Add the screenshot to a buffered file
			BufferedImage fullImg = ImageIO.read(screenshotFile);
			
			// Get the location of element on the page
			Point point = element.getLocation();
			
			// Get width and height of the element
			int eleWidth = element.getSize().getWidth();
			int eleHeight = element.getSize().getHeight();
			
			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
			    eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshotFile);
			
			//now copy the  screenshot to desired location using copyFile method
			FileUtils.copyFile(screenshotFile, new File(sFileName));
		 
			Message = "Logging | captureElementScreenshot | Element Screenshot file created successfully: " + sFileName;
			logger.info(Message);
			//Reporter.log(Message);
			return Logging.elementscreenshotfile = sFileName;
		} 
		catch (IOException e){
			Assertions.fail("Logging | captureElementScreenshot | Error:", e);
			logger.error("Logging | captureElementScreenshot | Error:", e);
			return null;
		}
	}

}

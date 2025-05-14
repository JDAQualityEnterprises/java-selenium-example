package com.jda.core;

import com.google.common.base.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;

public class Support {

	//private static WebDriver driver;
	//private static WebElement element = null;
	private static final Logger logger = LogManager.getLogger("Driver");
	private static String Message = null;

	public static void openURL(WebDriver driver, String Url) {
		try {
			Message = "Support.openURL: " + Url;
			logger.info(Message);
			//Reporter.log(Message);

			//Launch the URL
			driver.get(Url);

			//driver.navigate().to(Url);
		} catch (Exception e) {
			Message = "Support.openURL.Error opening URL: " + Url;
			logger.error(Message, e);
			fail(Message, e);
		}
	}

	//Create a folder by passing in the name of the folder
	public static void createFolder(String FolderName) {
		try {
			File file = new File(FolderName);
			if (!file.exists()) {
				if (file.mkdir()) {
					Message = "Support.newFolder.Folder: '" + FolderName + "' was created successfully";
					logger.info(Message);
					//Reporter.log(Message);
				}
				else {
					Message = "Support.newFolder.Failed to create folder: '" + FolderName + "'";
					logger.fatal(Message);
					fail(Message);
				}
			}
			else {
				Message = "Support.newFolder.Folder: '" + FolderName + "' already exists...";
				logger.info(Message);
				//Reporter.log(Message);
			}

		}
		catch (Exception e) {
			Message = "Support.newFolder.Error";
			logger.error(Message, e);
			fail(Message, e);
		}
	}
/*
	//test to see if we could overload methods
	public static boolean newFolder(String FolderName, String DateFormat){
		File currentDirectory = new File(new File(".").getAbsolutePath());
		try {
			System.out.println("Canon Path: " + currentDirectory.getCanonicalPath());
			System.out.println("Abs Path: " + currentDirectory.getAbsolutePath());
			System.out.println("Path: " + currentDirectory.getPath());
			System.out.println("User Dir: " + System.getProperty("user.dir"));
		}
		catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	public static void createFile(String FileName){
		try {
			File file = new File(FileName);
			if (!file.exists()) {
				if (file.createNewFile()) {
					Message = "Support.createFile.File: '" + FileName + "' was created successfully";
					logger.info(Message);
					Reporter.log(Message);
				}
				else {
					Message = "Support.createFile.Failed to create file: '" + FileName + "'";
					logger.fatal(Message);
					Assert.fail(Message);
				}
			}
			else {
				Message = "Support.createFile.File: '" + FileName + "' already exists";
				logger.info(Message);
				Reporter.log(Message);
			}
		}
		catch (Exception e) {
			Message = "Support.newFolder.Error creating file '" + FileName + "': ";
			logger.error(Message, e);
			Assert.fail(Message, e);
		}

	}
*/
	//Compare the Actual text to the Expected text
	public static boolean TextCompare(String ExpText, String ActText){
		int iCompare;
		try {
			iCompare = ActText.compareTo(ExpText);
			if (iCompare == 0) {
				Message = "Support.TextCompare.Text matches - \r\n" + "Expected: " + ExpText + "\r\n" + "Actual: " + ActText;
				logger.debug(Message);
				return true;
			} else {
				Message = "Support.TextCompare.Text does not match- \r\n" + "Expected: " + ExpText + "\r\n" + "Actual: " + ActText;
				logger.debug(Message);
				return false;
			}
		} catch (Exception e) {
			Message = "Support.TextCompare.Error";
			//Reporter.log(Message + ": " + e);
            logger.error("{}: {}", Message, e.getMessage());
			return false;
		}
	}//End of TextCompare

	//Check if the Actual text contains the Expected text
	public static boolean TextContains(String ExpText, String ActText){
		boolean bolCompare;
		try {
			bolCompare = ActText.contains(ExpText);
			if (bolCompare) {
				Message = "Support.TextContains.PASS - \r\n" + "Expected: " + ExpText + "\r\n" + "Actual: " + ActText;
				logger.debug(Message);
				return true;
			} else {
				Message = "Support.TextContains.FAIL - \r\n" + "Expected: " + ExpText + "\r\n" + "Actual: " + ActText;
				logger.debug(Message);
				return false;
			}
		} catch (Exception e) {
			Message = "Support.TextContains.Error - ";
			//Reporter.log(Message + ": " + e);
            logger.fatal("{}: {}", Message, e);
			return false;
		}
	}//End of TextCompare

	/*
	public static boolean CheckPageUrl_Compare(WebDriver driver, String ExpPageUrl) {
		try{
			//Get current page's Url
			String ActUrl = driver.getCurrentUrl();
			//Compare Url with expected
			if (TextCompare(ExpPageUrl, ActUrl)) {
				Message = "Page Url matches expected. \r\n"
						+ "Expected Url: " + ExpPageUrl + "\r\n" + "Actual Url: " + ActUrl;
				logger.info("Support.CheckPageUrl." + Message);
				Reporter.log("Support.CheckPageUrl." + Message);

				return true;
			}
			else {
				Message = "Page Url does NOT match expected. \r\n"
						+ "Expected Url: " + ExpPageUrl + "\r\n" + "Actual Url: " + ActUrl;
				logger.info("Support.CheckPageUrl." + Message);
				Reporter.log("Support.CheckPageUrl." + Message);

				return false;
			}
		}
		catch (Exception e) {
			Message = "Support.CheckPageUrl.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return false;
		}

	}//End of CheckPageUrl_Compare
	*/

	public static boolean CheckObjectExists(WebElement element) {
		try{
			if (element != null) {
				if (element.isDisplayed()) {
					Message = "Support.CheckObjectExists.Object exists and is displayed";
					logger.debug(Message);
					return true;
				}
				else {
					Message = "Support.CheckObjectExists.Object exists but is NOT displayed";
					logger.debug(Message);
					return false;
				}
			}
			else {
				Message = "Support.CheckObjectExists.Object does NOT exist";
				logger.debug(Message);
				return false;
			}
		}
		catch (Exception e) {
			Message = "Support.CheckObjectExists.Error - ";
			logger.error(Message, e);
			//Reporter.log(Message + ": " + e);
			return false;
		}

	}

	/*
	public static int GetLengthOfObjectText(WebElement element) {
		String sActText;
		Integer iLen;
		try {
			sActText = element.getAttribute("value");
			iLen = sActText.length();
			Message = "Support.GetLengthOfObjectText.Length of text is: " + iLen.toString();
			logger.debug(Message);
			return iLen;

		} catch (Exception e) {
			Message = "Support.GetLengthOfObjectText.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return 0;
		}
	}*/

	//Create a FluentWait that polls for x milliseconds and timeouts after y seconds and ignores NoSuchElementException
	public static FluentWait<WebDriver> setFluentWait(WebDriver driver, long PollTimeMilliSecs, long TimeoutSecs) {
		try{
			FluentWait<WebDriver> wait = new FluentWait<>(driver);
			wait.pollingEvery(Duration.ofMillis(PollTimeMilliSecs));
			wait.withTimeout(Duration.ofSeconds(TimeoutSecs));
			wait.ignoring(NoSuchElementException.class);
			wait.ignoring(StaleElementReferenceException.class);

			return wait;
		}
		catch (Exception e) {
			Message = "Support.setFluentWait.Error - ";
			logger.error(Message, e);
			//Reporter.log(Message + ": " + e);
			return null;
		}
	}

	public static void ScrollToElement(WebDriver driver, WebElement element) throws InterruptedException
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
	}

	/*
	//Return a list of Label elements under a Parent element
	public static List<WebElement> GetChildLabels(WebDriver driver, WebElement ParentElement) {
		List<WebElement> LabelElements;
		try {
			LabelElements = ParentElement.findElements(By.xpath(".//label"));
			return LabelElements;
		} catch (Exception e) {
			Message = "Support.GetChildLabels.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetChildLabels
	*/

	//Return a list of Label elements under a Parent element using the label name
	public static List<WebElement> GetChildLabels(WebElement ParentElement, String LabelName) {
		List<WebElement> LabelElements;
		try {
			LabelElements = ParentElement.findElements(By.xpath(".//label[@title='" + LabelName + "']"));
			return LabelElements;
		} catch (Exception e) {
			Message = "Support.GetChildLabels.Error - ";
			logger.error(Message, e);
			//Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetChildLabels - overload

	/*
	//Return one Label element under a Parent element using the label name
	public static WebElement GetChildLabel(WebDriver driver, WebElement ParentElement, String LabelName) {
		WebElement elemLabel;
		List<WebElement> LabelElements;
		int NumOfLabels;
		try {
			//Get the list of child labels from the parent
			LabelElements = GetChildLabels(driver, ParentElement, LabelName);

			//Error in GetChildLabels function, return null
			if (LabelElements == null) {
				Message = "Null labels returned with name: " + LabelName + ". Returning a null value";
				logger.info("Support.GetChildLabel." + Message);
				Reporter.log("Support.GetChildLabel." + Message);
				return null;
			}

			//Verify we have one label that matches the expected Unit number
			NumOfLabels = LabelElements.size();
			switch (NumOfLabels) {
				case 0:
					//0 labels found
					Message = "No labels were found under the parent with name: " + LabelName + ". Returning a null value";
					logger.info("Support.GetChildLabel." + Message);
					Reporter.log("Support.GetChildLabel." + Message);
					return null;
				case 1:
					//Label found
					Message = "One label found under the parent with name: " + LabelName;
					logger.info("Support.GetChildLabel." + Message);
					Reporter.log("Support.GetChildLabel." + Message);
					elemLabel = LabelElements.get(0);
					return elemLabel;
				default:
					//Multiple Labels found
					Message = "Multiple labels were found under the parent with name: " + LabelName + ". Returning a null value";
					logger.info("Support.GetChildLabel." + Message);
					Reporter.log("Support.GetChildLabel." + Message);
					return null;
			}
		} catch (Exception e) {
			Message = "Support.GetChildLabel.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetChildLabel
	*/

	//Return a list of Checkbox elements under a Parent element using the label name
	public static List<WebElement> GetChildCheckboxes(WebElement ParentElement) {
		List<WebElement> ChkBoxElements;
		try {
			ChkBoxElements = ParentElement.findElements(By.xpath(".//checkbox"));
			return ChkBoxElements;
		} catch (Exception e) {
			Message = "Support.GetChildCheckboxes.Error - ";
			logger.error(Message, e);
			//Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetChildCheckboxes

	//Return one Checkbox element under a Parent
	/*
	public static WebElement GetChildCheckBox(WebDriver driver, WebElement ParentElement) {
		WebElement elemChkBox;
		List<WebElement> ChkBoxElements;
		int NumOfLabels;
		try {
			//Get the list of child labels from the parent
			ChkBoxElements = GetChildCheckboxes(driver, ParentElement);

			//Error in GetChildLabels function, return null
			if (ChkBoxElements == null) {
				Message = "Null checkboxes returned. Returning a null value";
				logger.info("Support.GetChildCheckBox." + Message);
				Reporter.log("Support.GetChildCheckBox." + Message);
				return null;
			}

			//Verify we have one checkbox that matches the expected Unit number
			NumOfLabels = ChkBoxElements.size();
			switch (NumOfLabels) {
				case 0:
					//0 Checkboxes found
					Message = "No checkboxes were found under the parent. Returning a null value";
					logger.info("Support.GetChildCheckBox." + Message);
					Reporter.log("Support.GetChildCheckBox." + Message);
					return null;
				case 1:
					//Checkbox found
					Message = "One checkbox found under the parent";
					logger.info("Support.GetChildCheckBox." + Message);
					Reporter.log("Support.GetChildCheckBox." + Message);
					elemChkBox = ChkBoxElements.get(0);
					return elemChkBox;
				default:
					//Multiple Checkboxes found
					Message = "Multiple checkboxes were found under the parent. Returning a null value";
					logger.info("Support.GetChildCheckBox." + Message);
					Reporter.log("Support.GetChildCheckBox." + Message);
					return null;
			}
		} catch (Exception e) {
			Message = "Support.GetChildCheckBox.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetChildCheckBox
	*/

	/*
	//Return a Fleet Span element based on Fleet Name (should only return one)
	public static WebElement GetFleetSpan(WebDriver driver, String FleetName) {
		List<WebElement> FleetElements;
		WebElement elemSpan;
		int NumOfSpans;
		try {
			FleetElements = driver.findElements(By.xpath("//span[contains(text(),'" + FleetName + "')]"));

			//Verify we have one span that matches the expected fleet name
			NumOfSpans = FleetElements.size();
			switch (NumOfSpans) {
				case 0:
					//0 spans found
					Message = "No spans were found with Fleet name of '" + FleetName + "'. Returning a null value";
					logger.info("Support.GetFleetSpan." + Message);
					Reporter.log("Support.GetFleetSpan." + Message);
					return null;
				case 1:
					//span found
					Message = "One span found with Fleet name of '" + FleetName + "'";
					logger.info("Support.GetFleetSpan." + Message);
					Reporter.log("Support.GetFleetSpan." + Message);
					elemSpan = FleetElements.get(0);
					return elemSpan;
				default:
					//Multiple spans found
					Message = "Multiple spans were found with Fleet name of '" + FleetName + "'. Returning a null value";
					logger.info("Support.GetFleetSpan." + Message);
					Reporter.log("Support.GetFleetSpan." + Message);
					return null;
			}
		} catch (Exception e) {
			Message = "Support.GetFleetSpan.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetFleetSpan
	*/

	/*
	//Return a list of Fleet Div elements
	public static List<WebElement> GetFleetDivs(WebDriver driver) {
		List<WebElement> FleetElements;
		try {
			FleetElements = driver.findElements(By.xpath("//div[@id='vd-headline-list']//div[@class='vd-expander ng-scope']"));
			return FleetElements;
		} catch (Exception e) {
			Message = "Support.GetFleetDivs.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetFleetDivs
	*/

	/*
	//Get the Fleet information from the fist child of the Fleet parent span
	public static WebElement GetFleetInfo(WebDriver driver, WebElement ParentElement) {
		WebElement elemFleetInfoSpan;
		try {
			//Get the parent Fleet span
			elemFleetInfoSpan = ParentElement.findElement(By.xpath("//span[@class='ng-binding']"));

			//Error in GetFleetSpan function, return null
			if (elemFleetInfoSpan == null) {
				Message = "Fleet Info span not found. Returning a null value";
				logger.info("Support.GetFleetInfo." + Message);
				Reporter.log("Support.GetFleetInfo." + Message);
				return null;
			}
			return null;

		} catch (Exception e) {
			Message = "Support.GetFleetInfo.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
			return null;
		}
	}//End of GetFleetInfo
*/

    public static void CheckForWaitingPopup(WebDriver driver) {
        long pollTime = 500;
        long timeout =  10;

        try {
			Thread.sleep(500);

		} catch	(Exception e) {
			// NO OP
		}

        CheckForWaitingPopup(driver, pollTime, timeout);
    }

	public static boolean CheckForWaitingPopup(WebDriver driver, long PollTimeMilliSecs, long TimeoutSecs){
		final boolean[] success = new boolean[1];

        logger.info("CheckForWaitingPopup-Start");

		try {
			FluentWait<WebDriver> Fwait;

			Fwait = new FluentWait<>(driver);
			Fwait.pollingEvery(Duration.ofMillis(PollTimeMilliSecs));
			Fwait.withTimeout(Duration.ofSeconds(TimeoutSecs));
			Fwait.ignoring(NoSuchElementException.class);
			Fwait.ignoring(StaleElementReferenceException.class);

			return Fwait.until((Function<WebDriver, Boolean>) arg0 -> {
                assert arg0 != null;
                WebElement element = arg0.findElement(By.xpath("//body"));
                String status = element.getDomAttribute("aria-busy");
                logger.info("The status of body, aria-busy is {}", status);
                assert status != null;

                if (status.equals("false")) {
                    success[0] = true;
                    return true;
                }
                return false;
            });
		} catch (Exception e) {
			// NO OP
		} finally {
            logger.info("CheckForWaitingPopup-End");
        }

        return success[0];
    }
/*
	public static void ExpandAllArrows(WebDriver driver, NgWebDriver ngWebDriver){
		List<WebElement> elem_Arrows;
		int size;

		try{
			elem_Arrows = driver.findElements(By.xpath("//i[contains(@class,'vd-icon ng-binding') and text()='menu_right']"));
			size = elem_Arrows.size();

			Iterator<WebElement> itr = elem_Arrows.iterator();

			while(itr.hasNext()) {
				new Actions(driver).moveToElement(itr.next()).click().perform();
				//itr.next().click();
				ngWebDriver.waitForAngularRequestsToFinish();
			}
		}
		catch (Exception e){
			Message = "Support.ExpandAllArrows.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
		}

	}*/

/*
	public static void CollapseAllArrows(WebDriver driver, NgWebDriver ngWebDriver){
		List<WebElement> elem_Arrows;
		int size;

		try{
			elem_Arrows = driver.findElements(By.xpath("//i[contains(@class,'vd-icon ng-binding') and text()='arrow_drop_down']"));
			size = elem_Arrows.size();

			Iterator<WebElement> itr = elem_Arrows.iterator();

			while(itr.hasNext()) {
				itr.next().click();
				ngWebDriver.waitForAngularRequestsToFinish();
			}
		}
		catch (Exception e){
			Message = "Support.CollapseAllArrows.Error - ";
			logger.error(Message, e);
			Reporter.log(Message + ": " + e);
		}

	}*/

    public static void SelectDateFromCalendar(WebDriver driver, Date MyDate){
		String day, month, year, calMonthYear;
		WebElement left, right, heading, dayToClick;
		List<WebElement> days;
		FluentWait<WebDriver> Fwait = null;
		boolean doMonth = false, doYear = false, doDay = false;
        WebElement Calendar = driver.findElement(By.xpath("//div[@class='ng-scope ng-isolate-scope uib-datepicker']"));

        Fwait = setFluentWait(driver, 500, 10);
		assert Fwait != null;

		SimpleDateFormat yearFormater = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFormater = new SimpleDateFormat("MMMM");
		SimpleDateFormat dayFormater = new SimpleDateFormat("dd");

		year = yearFormater.format(MyDate);
		month = monthFormater.format(MyDate);
		day = dayFormater.format(MyDate);

		left = Calendar.findElement(By.xpath(".//button[@ng-click='move(-1)']"));

        Fwait.until(ExpectedConditions.elementToBeClickable(left));
		right = Calendar.findElement(By.xpath(".//button[@ng-click='move(1)']"));
		Fwait.until(ExpectedConditions.elementToBeClickable(right));
		heading = Calendar.findElement(By.xpath(".//button[@role='heading']/strong"));
		Fwait.until(ExpectedConditions.elementToBeClickable(heading));

		//What component
		calMonthYear = heading.getText();
        if (!TextContains(year, calMonthYear)){
            doYear = true;
            heading.click(); //If we are selecting the year, then we need to click heading twice
			Fwait.until(ExpectedConditions.elementToBeClickable(heading));
			heading.click(); //Once for the month, and again for the year
        }else if (!TextContains(month, calMonthYear)){
            day = month;
            doMonth = true;
            heading.click();
        }else{
            doDay = true;
        }

        logger.debug("doYear={} doMonth={}doDay={}\n", doYear, doMonth, doDay);
        boolean dayInMonth = false;

        days = Calendar.findElements(By.xpath(".//button[@ng-click='select(dt.date)']"));
        Iterator<WebElement> iterator = days.iterator();
        logger.debug("Have '{}' cells to iterate \n", days.size());
        while(iterator.hasNext()){
            dayToClick = iterator.next();
            String cellText = dayToClick.getText();
            logger.debug("Cell text '{}' contains '{} ?'\n", cellText, day);

		//Month end, we are only ever going to click a number within the month in view
		if (doDay) {
			int dayFromCalendar = Integer.parseInt(cellText);
			if (dayFromCalendar == 1){
				dayInMonth = true;
			}
		} else {
			dayInMonth = true;
		}

		if (Support.TextContains(day, cellText) && dayInMonth){
			dayToClick.click();
			break;
		}
	}

	if(!doDay) //No need to recurse if the day was selected
	   SelectDateFromCalendar(driver, MyDate);

	left = null;
	right = null;
	heading = null;
	days = null;
	dayToClick = null;
	}

    public static void selectDropDownMenuItem(WebElement button, String option) {
        FluentWait<WebDriver> Fwait = null;
        WebDriver driver = BrowserDriver.getDriver();

        Fwait = Support.setFluentWait(driver, 500, 10);
        Fwait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();

        WebElement listItem = driver.findElement(By.xpath(".//a[contains(text(),'"+option+"')]"));
        Fwait.until(ExpectedConditions.visibilityOf(listItem));
        listItem.click();
    }

     /* =================================================================================================================
    OBJECTIVE
        Click the spin control on the specified edit box, until the value matches targetNum

    OPERATIONAL NOTES
        N/A

    ================================================================================================================= */

    public static void setEditValueBySpinControl(WebElement editBox, int targetNum) {
		WebElement spinUp = editBox.findElement(By.xpath("..//a[@class='stepper-btn-up']"));
		WebElement spinDown = editBox.findElement(By.xpath("..//a[@class='stepper-btn-dwn']"));
		boolean done = false;

		do {
			String val = editBox.getDomAttribute("value");
			logger.debug("Have '{}' spinner current value iterate \n", val);
            assert val != null;

            int currentVal = Integer.parseInt(val);

			if (currentVal == targetNum) {
				done = true;
			} else if (currentVal > targetNum) {
				spinDown.click();
			} else {
				spinUp.click();
			}
		} while (!done);
	}

}//End of Support class
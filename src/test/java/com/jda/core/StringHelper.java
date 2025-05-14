package com.jda.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.InvalidArgumentException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/// <summary>
    /// A class to capture errors for tests with multiple asserts
    /// </summary>
    public class StringHelper {

    public static String userNameFromDomainAndUsername(String string){
        int indexOfDeomainSeperator = string.indexOf('\\');
        String domain = string.substring(0,indexOfDeomainSeperator+1);
        return string.substring(indexOfDeomainSeperator+1,string.length());
    }

    public static String createDateStringFromMask(String inputDate, String format)
    {
        boolean replaceTime = false;
        String toCreateDate = inputDate;
        String toFormatToDate = null;
        String savedTimeComponent = "";
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance(); // this would default to now
        String[] components = inputDate.split(" ");

        if (toCreateDate.contains("TODAY"))
        {
            if(components.length > 1){
                toCreateDate = components[0];
                savedTimeComponent = components[1];
                replaceTime = true;
            }

            if(toCreateDate.contains("-")){
                String[] amount = toCreateDate.split("-");
                int offset = Integer.parseInt(amount[1]);
                cal.add(Calendar.DAY_OF_MONTH, -offset);
                toFormatToDate = dateFormat.format(cal.getTime());

            }else if (toCreateDate.contains("+")){
                String[] amount = toCreateDate.split("\\+");
                int offset = Integer.parseInt(amount[1]);
                cal.add(Calendar.DAY_OF_MONTH, offset);
                toFormatToDate = dateFormat.format(cal.getTime());

            }else if (toCreateDate.equals("TODAY")){
                //Nothing to change
                toFormatToDate = dateFormat.format(cal.getTime());
            }
        }else{
            toFormatToDate = toCreateDate; //Nothing to format
        }//End if we need to substitute the date

        //If time was specified, put back time in the string (replacing current time()
        if(replaceTime){
            String[] finalComponents = toFormatToDate.split(" ");
            return (finalComponents[0] + " " + savedTimeComponent);
        }else{
            return toFormatToDate;
        }
    }

    public static Date createDateFromString(String toCreateDate, String format)
    {
        String toFormatToDate = createDateStringFromMask(toCreateDate, format);
        return  formatDateFromString(toFormatToDate, format);
    }

    public static Date formatDateFromString(String toFormatToDate, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(toFormatToDate);
        } catch (Exception e){
            Assertions.fail("Failed to format '" + toFormatToDate + "' for format '" + format + "'" + e);
            return null;
        }
    }

    public static String stringToCase(String string, String caseType) {
        String inputString = "";
        switch (caseType){
            case "UPPER":
                inputString = string.toUpperCase();
                break;
            case "lower":
                inputString = string.toLowerCase();
                break;
            case "Title":
                //No op
                int indexOfDeomainSeperator = string.indexOf('\\');
                String domain = string.substring(0,indexOfDeomainSeperator+1);
                String username = string.substring(indexOfDeomainSeperator+1,string.length());
                username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
                inputString = domain + username;
                break;
        }
        return inputString;
    }

    public static String modifyDateFromString(String inputDate, Operator sign, int offsetAmount, String type, String format) {
        int offsetType;
        DateFormat dateFormat = null;
        Calendar cal = Calendar.getInstance();

        switch(type){
            case "Sec":
            case "Secs":
            case "Seconds":
                offsetType = Calendar.SECOND;
                break;
            case "Minute":
            case "Minutes":
                offsetType = Calendar.MINUTE;
                break;
            case "Hour":
            case "Hours":
                offsetType = Calendar.HOUR_OF_DAY;
                break;
            case "Day":
            case "Days":
                offsetType = Calendar.DAY_OF_MONTH;
                break;
            default:
                throw new InvalidArgumentException("Type '"+ type + "' not known, should be: Sec, Minute, Hour or Day");
        }

        try {
            dateFormat = new SimpleDateFormat(format);
            cal.setTime(dateFormat.parse(inputDate));

            if (Operator.ADDITION == sign) {
                cal.add(offsetType, offsetAmount);
            }else if (Operator.SUBTRACTION == sign){
                cal.add(offsetType, -offsetAmount);
            }else{
                throw new InvalidArgumentException("Sign '"+ sign + "' not valid");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat.format(cal.getTime());
    }

    public static boolean dateWithinTolerance(String actualDateTime, String expectedDateTime, int toleranceAmount, String type, String format) {
        boolean success = true;

        String lowerLimit = StringHelper.modifyDateFromString(expectedDateTime, Operator.SUBTRACTION, toleranceAmount, type, format);
        String upperLimit = StringHelper.modifyDateFromString(expectedDateTime, Operator.ADDITION, toleranceAmount, type, format);
        Date dtLowerLimit = StringHelper.createDateFromString(lowerLimit, format);
        Date dtUpperLimit = StringHelper.createDateFromString(upperLimit, format);
        Date dtActualDateTime = StringHelper.createDateFromString(actualDateTime, format);

        if ((!dtActualDateTime.before(dtUpperLimit) && !dtActualDateTime.equals(dtUpperLimit)) ||
                (!dtActualDateTime.after(dtLowerLimit) && !dtActualDateTime.equals(dtLowerLimit))){
            success = false;
        }
        return success;
    }

    public static String methodNameFromTestInfo(TestInfo info){
        String methodName = info.getTestMethod().toString();
        int nameIdx = methodName.lastIndexOf(".")+1;
        return methodName.substring(nameIdx, methodName.length()-3);
    }
}

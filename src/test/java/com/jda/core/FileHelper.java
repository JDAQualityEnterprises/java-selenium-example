package com.jda.core;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;


/* =====================================================================================================================
OBJECTIVE
    To provide an auto generated value at runtime and reuse during an entire Cucumber run
    This means, that preferable to have a Run file for each set of related feature files

===================================================================================================================== */
public class FileHelper {

    // Set to auto generate a value
    private static Boolean autoGenerate = true;
    private static Boolean _runOnce = false;
    private static String _iterationFolder = "";
    private static String _basePath = "";
    private static String featureName = "data";

    // default constructor
    public FileHelper() {}

    public static Boolean runOnce() {
        return _runOnce;
    }
    public static void runOnce(Boolean value) {
        _runOnce = value;
    }

    public static String iterationFolder() {
        if (_iterationFolder.equals("")) {
            _iterationFolder = FullTagValue();
        }
        return _iterationFolder;
    }


    // Returns the location of the properties file
    private static String filePath() {
        return _basePath.concat("\\").concat(_iterationFolder).concat("\\Data\\").concat(featureName).concat(".properties");
    }

    // Generates the new tag datetime to value
    private static String tagValue() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Generates the new tag datetime to value
    private static String FullTagValue() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    /* =================================================================================================================
    OBJECTIVE
         Called at the beginning of a run to delete the file

    OPERATIONAL NOTES
        N/A

    ================================================================================================================= */
    public static void initialiseRunTimeData(String basePath) {
        _basePath = basePath;
        File file = new File(filePath());

        try {
            Files.deleteIfExists(file.toPath());

            String templatePath = System.getProperty("user.dir").concat("\\src\\test\\resources\\runtime.properties");

            Path copy_from = Paths.get(templatePath);
            Path copy_to = Paths.get(filePath());

            Files.copy(copy_from, copy_to, REPLACE_EXISTING);

        }
        catch (Exception e) {

        }

    }


    /* =================================================================================================================
    OBJECTIVE
         Either generates or simply returns the value

    OPERATIONAL NOTES
        This need to be called from the Definition files
        Prefix the value with "<auto>", without quotes

    ================================================================================================================= */
    public static String autoGenerate(String dataValue) {
        // Check if auto Generate is on
        if (!autoGenerate) {
            return dataValue;
        }

        return readProperty(dataValue, dataValue);

    }


    /* =================================================================================================================
    OBJECTIVE
         Reads a property value if found returns it, else generates and updates file

    OPERATIONAL NOTES
        N/A

    ================================================================================================================= */
    public static String readProperty(String dataName, String dataValue) {

        try {
            Properties properties = getProperties();

            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();

                if (key.equals(dataName) ) {
                    String value = properties.getProperty(key);
                    if (!value.equals("<auto>")) {
                        return properties.getProperty(key);
                    } else {
                        String newValue = dataValue.concat(tagValue());
                        writeProperty(dataName, newValue);
                        return newValue;
                    }
                }
            }
        } catch (Exception e) {
            return dataValue;
        }

        return dataValue;
    }

    public static String readProperty(String dataName) {
        return readProperty(dataName, ""); //No tag value for just reading an item
    }


    /* =================================================================================================================
    OBJECTIVE
         Reads all properties from file

    OPERATIONAL NOTES
        N/A

    ================================================================================================================= */
    private static Properties getProperties() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(filePath());

            // load a properties file
            properties.load(input);

            return properties;

        }
        catch (Exception e) {
            return properties;
        }

    }


    /* =================================================================================================================
    OBJECTIVE
         Writes a property pair to the file, either new or update

    OPERATIONAL NOTES
        N/A

    ================================================================================================================= */
    public static void writeProperty(String dataName, String dataValue) {
        try {
            Properties properties = getProperties();
            properties.setProperty(dataName, dataValue);

            File file = new File(filePath());
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut , null);
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* =================================================================================================================
       OBJECTIVE
            Compare two CSV files, a row at a time. Returns empty string on success

       OPERATIONAL NOTES
           ignoreHeader = if set to true, then the header is not compared

       ================================================================================================================= */
    public static String compareCsvFiles(String actualFullFilePath, String expectedFullFilePath, boolean ignoreHeader){
        boolean success = true;
        String results = "";
        CSVParser actualIn = null;
        CSVParser expectedIn = null;

        try{
            int currentRecord = 0;
            actualIn = CSVParser.parse(new BufferedReader(new FileReader(actualFullFilePath)), CSVFormat.DEFAULT);
            expectedIn = CSVParser.parse(new BufferedReader(new FileReader(expectedFullFilePath)), CSVFormat.DEFAULT);

            List<CSVRecord> actualRecords = actualIn.getRecords();
            List<CSVRecord> expectedRecords = expectedIn.getRecords();

            int actualSize = actualRecords.size();
            int expectedSize = expectedRecords.size();

            if (actualSize != expectedSize) {
                results = "TEST FAILURE: Records from actual and expected don't match\n";
                success = false;
            }

            while(success && currentRecord < actualSize){

                //Skip the header
                if (!ignoreHeader || currentRecord >= 1) {
                    CSVRecord expectedRecord = expectedRecords.get(currentRecord);
                    CSVRecord actualRecord = actualRecords.get(currentRecord);

                    String actual = actualRecord.toString();
                    String expected = expectedRecord.toString();
                    if (!actual.equals(expected)){
                        success = false;
                        results = "TEST FAILURE: Actual '" + actual + "' doesn't match '" + expected + "' on row " + currentRecord;
                    }
                }
                currentRecord++;
            }

        }catch(Exception e){
            //No-op
        }finally{
            try {
                expectedIn.close();
                actualIn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return results;
    }


    /* =================================================================================================================
       OBJECTIVE
            Reads, Appends or Writes to files

       OPERATIONAL NOTES
           N/A

       ================================================================================================================= */
    public static void writeToFile(String fileTarget, String contents) throws IOException {
        Files.deleteIfExists(Paths.get(fileTarget));
        Files.write(Paths.get(fileTarget), contents.getBytes());
    }

    public static String readFromFile(String fileSource) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileSource)));
    }

    public static void appendToFile(String fileTarget, String contents) throws IOException {
        Files.write(Paths.get(fileTarget), contents.getBytes(), APPEND );
    }




}

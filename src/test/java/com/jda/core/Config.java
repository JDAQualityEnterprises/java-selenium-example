package com.jda.core;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public final class Config {

    private static final Properties properties = new Properties();

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            properties.load(loader.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getSetting(String key) {
        String returnValue = properties.getProperty(key);

        if (returnValue == null) {
            return "";
        }

        return returnValue;
    }

    public static String getSetting(String key, String defaultValue) {
        String returnValue = properties.getProperty(key);

        if (returnValue == null) {
            return defaultValue;
        }

        return returnValue;
    }




    public static void changeSetting(String key, String value) {

        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            properties.setProperty(key, value);
            // save properties to project root folder
            properties.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
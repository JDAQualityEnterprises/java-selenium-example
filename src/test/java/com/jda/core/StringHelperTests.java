package com.jda.core;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringHelperTests {

    @Test
    public void TestStringFormat() {

        // Call String.format with three integer codes.
        String result = String.format("One: %1$d Two: %2$d Three: %3$d",
                10, 20, 30);
        System.out.println(result);


        String first = "Marcus";
        String last = "Aurelius";

        // Use simple string format.
        String value = String.format("this is%s this is %s", first, last);
        System.out.println(value);
//
//        // Use indexes before simple string format.
//        value = String.format("%1s %2s", first, last);
//        System.out.println(value);
//
//        // Use $ symbol before string character.
//        value = String.format("%1$s %2$s", first, last);
//        System.out.println(value);
    }
}

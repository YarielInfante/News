package com.upday.News.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class takes care of Date operations.
 */
public final class DateUtil {

    public static final String PATTERN = "MM-dd-yyyy";

    /**
     * Converts a string date into a Date instance based on pattern. If an error occurs it returns a null.
     *
     * @param date date string wanted to convert.
     * @return an instance of the date converted
     * @see Date
     * @see SimpleDateFormat
     * @see DateUtil#PATTERN
     */
    public static Date stringToDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);

        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converts an instance of Date into a String based on pattern.
     *
     * @param date an instance of Date wanted to convert.
     * @return a String of date converted.
     */
    public static String dateToString(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);

        return dateFormat.format(date);
    }
}

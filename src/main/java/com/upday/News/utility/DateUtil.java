package com.upday.News.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class takes care of Date operations.
 */
public final class DateUtil {

    public static final String PATTERN = "dd-MM-yyyy";
    public static final LocalDate DATE_MIN = LocalDate.of(1900, 1, 1);
    public static final LocalDate DATE_MAX = LocalDate.of(3000, 1, 1);

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

    /**
     * Returns a date with a year 1900.
     *
     * @return an instance of Date
     */
    public static Date minDate() {

        return Optional.of(DATE_MIN).map(localDateToDate).get();

    }

    /**
     * Returns a date with a year 3000.
     *
     * @return an instance of Date
     */
    public static Date maxDate() {

        return Optional.of(DATE_MAX).map(localDateToDate).get();
    }

    /**
     * Converts a LocalDate into a Date
     */
    public static final Function<LocalDate, Date> localDateToDate = localDate -> Date.from(
            localDate
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
    );

}

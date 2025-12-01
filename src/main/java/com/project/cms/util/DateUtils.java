package com.project.cms.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class handling date formatting and calculation operations.
 * <p>
 * Default date format: <b>dd/MM/yyyy</b> (Day/Month/Year)
 */
public class DateUtils {
    
    // Date format: Day/Month/Year
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Converts a String date (e.g., "29/11/2025") to a LocalDate object.
     * @param dateStr The formatted date string.
     * @return The converted LocalDate object, or null if the format is invalid.
     */
    public static LocalDate stringToDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts a LocalDate object to a String format (dd/MM/yyyy).
     * @param date The date object.
     * @return The formatted date string.
     */
    public static String dateToString(LocalDate date) {
        if (date == null) return "";
        return date.format(FORMATTER);
    }

    /**
     * Calculates the age based on the given birth date.
     * @param birthDate The birth date.
     * @return The calculated age (in years).
     */
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Checks if the given date is today.
     * @param date The date to check.
     * @return true if the date is today, otherwise false.
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) return false;
        return date.equals(LocalDate.now());
    }

    /**
     * Checks if the given date is in the past.
     * @param date The date to check.
     * @return true if the date is before today.
     */
    public static boolean isPastDate(LocalDate date) {
        if (date == null) return false;
        return date.isBefore(LocalDate.now());
    }
}

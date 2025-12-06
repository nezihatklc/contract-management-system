package com.project.cms.ui.input;

import com.project.cms.util.ConsoleColors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class responsible for safely reading all user input from the console.
 * <p>
 * Provides validated input methods for integers, doubles, strings,
 * passwords, dates, and phone numbers. Also includes a wait-for-enter helper.
 * <br>This class ensures consistent validation and error messaging across the application.
 * @author Zeynep Sıla Şimşek
 */

public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_FORMAT);

    // ---------------------- INTEGER ----------------------

    /**
     * Reads an integer value from the user .
     *
     * @param prompt Message displayed to the user
     * @return Valid integer
     */
    public static int readInt(String prompt) {
        return readInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Reads an integer within the specified minimum and maximum range.
     * Continues prompting until valid input is given.
     *
     * @param prompt Message shown to user
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Valid integer within range
     */

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            if (min == Integer.MIN_VALUE && max == Integer.MAX_VALUE) {
                ConsolePrinter.prompt(prompt);
            } else {
                ConsolePrinter.prompt(prompt + " (" + min + "-" + max + ")");
            }
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); 

                if (value >= min && value <= max) {
                    return value;
                } else {
                    ConsolePrinter.error("Please enter a value between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                ConsolePrinter.error("Invalid entry. Please enter only integers.");
                scanner.nextLine(); 
            }
        }
    }

    // ---------------------- DOUBLE ----------------------

    /**
     * Reads a decimal number from user input.
     * Retries on invalid input.
     *
     * @param prompt Text shown before asking for input
     * @return Valid double value
     */
    public static double readDouble(String prompt) {
        while (true) {
            ConsolePrinter.prompt(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                ConsolePrinter.error("Please enter a valid decimal number.");
                scanner.nextLine();
            }
        }
    }

    // ---------------------- STRING ----------------------

    /**
     * Reads a string input from the user.
     * Optionally enforces mandatory (non-empty) input.
     *
     * @param prompt Text displayed on console
     * @param isMandatory Whether empty input is allowed
     * @return Trimmed string from user
     */
    public static String readString(String prompt, boolean isMandatory) {
        while (true) {
            ConsolePrinter.prompt(prompt);
            String input = scanner.nextLine();

            if (input == null) {
                input = "";
            }

            if (isMandatory && input.trim().isEmpty()) {
                ConsolePrinter.error("This field cannot be left blank.");
            } else {
                return input.trim();
            }
        }
    }

    // ---------------------- PASSWORD ----------------------

    /**
     * Reads a password from user input.
     * <p>Note: Console masking is not implemented; input will be visible.
     *
     * @param prompt Label for the password field
     * @return Non-empty password string
     */
    public static String readPassword(String prompt) {
        return readString(prompt + " (Your input will be visible)", true);
    }

    // ---------------------- DATE ----------------------

    /**
     * Reads a date from the user using format dd/MM/yyyy.
     * Validates format and optionally enforces mandatory input.
     *
     * @param prompt Message displayed to user
     * @param isMandatory Whether the date field can be left empty
     * @return Parsed LocalDate or null if optional and left blank
     */
    public static LocalDate readDate(String prompt, boolean isMandatory) {
        while (true) {
            ConsolePrinter.prompt(prompt + " (" + DATE_FORMAT + ")");
            String dateString = scanner.nextLine().trim();

            if (dateString.isEmpty()) {
                if (isMandatory) {
                    ConsolePrinter.error("This field cannot be left blank.");
                    continue;
                } else {
                    return null;
                }
            }

            try {
                return LocalDate.parse(dateString, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                ConsolePrinter.error("Incorrect date format. Please use " + DATE_FORMAT + ".");
            }
        }
    }

    // ---------------------- PHONE ----------------------

    /**
     * Reads a phone number consisting only of digits (10–13 characters).
     * Validates formatting and optionally enforces mandatory input.
     *
     * @param prompt Message shown to user
     * @param isMandatory Whether phone number is required
     * @return Valid phone number string or null if optional
     */
    public static String readPhone(String prompt, boolean isMandatory) {

    while (true) {
        ConsolePrinter.prompt(prompt);
        String phone = scanner.nextLine().trim();

        if (phone.isEmpty()) {
            if (isMandatory) {
                ConsolePrinter.error("This field cannot be left blank.");
                continue;
            } else {
                return null; 
            }
        }

        
        if (!phone.matches("\\d{10,13}")) {
            ConsolePrinter.error("Phone number must contain only digits (10-13 digits).");
            continue;
        }

        return phone;
    }
}


    // ---------------------- WAIT ENTER ----------------------

    /**
     * Waits for the user to press ENTER before continuing.
     * Commonly used to pause after displaying important information.
     */
    public static void WaitEnter() {
        ConsolePrinter.blank();
        ConsolePrinter.promptInline(
                ConsoleColors.PURPLE + "Press ENTER to continue..." + ConsoleColors.RESET
        );
        scanner.nextLine();
        ConsolePrinter.blank();
    }
}


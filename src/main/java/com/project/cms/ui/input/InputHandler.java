package com.project.cms.ui.input;

import com.project.cms.util.ConsoleColors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_FORMAT);

    // ---------------------- INTEGER ----------------------
    public static int readInt(String prompt) {
        return readInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            ConsolePrinter.prompt(prompt + " (" + min + "-" + max + ")");
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
    public static String readPassword(String prompt) {
        return readString(prompt + " (Your input will be visible)", true);
    }

    // ---------------------- DATE ----------------------
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
    public static void WaitEnter() {
        ConsolePrinter.blank();
        ConsolePrinter.promptInline(
                ConsoleColors.PURPLE + "Press ENTER to continue..." + ConsoleColors.RESET
        );
        scanner.nextLine();
        ConsolePrinter.blank();
    }
}


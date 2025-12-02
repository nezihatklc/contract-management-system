package main.java.com.project.cms.ui.input;

    
import main.java.com.project.cms.ui.input.ConsolePrinter;
import main.java.com.project.cms.util.ConsoleColors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    private static Scanner scanner = new Scanner(System.in);

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }


    public static int readInt(String prompt) {
        while (true) {
            ConsolePrinter.prompt(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                ConsolePrinter.error("ınvalid entry. Please enter only integers.");
                scanner.nextLine();
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            ConsolePrinter.prompt(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                ConsolePrinter.error("Please enter a valid decimal number. ");
                scanner.nextLine();
            }
        }
    }


    public static String readString(String prompt, boolean isMandatory) {
        while (true) {
            ConsolePrinter.prompt(prompt);
            String input = scanner.nextLine().trim();

            if (isMandatory && input.isEmpty()) {
                ConsolePrinter.error("This field cannot be left blank.");
            } else {
                return input;
            }
        }
    }

    public static String readPassword(String prompt) {
        return readString(prompt, true);
    }

    

    public static LocalDate readDate(String prompt) {
        while (true) {
            ConsolePrinter.prompt(prompt + " (" + DATE_FORMAT + ")");
            String dateString = scanner.nextLine().trim();
            try {
                return LocalDate.parse(dateString, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                ConsolePrinter.error("Incorrect date format.");
            }
        }
    }

    

    public static void waitEnter() {
        ConsolePrinter.blank();
        ConsolePrinter.promptInline(
                ConsoleColors.PURPLE + "Press ENTER to continue..." + ConsoleColors.RESET
        );
        scanner.nextLine();
        ConsolePrinter.blank();
    }
}




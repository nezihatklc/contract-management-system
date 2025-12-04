package com.project.cms.ui.input;

import com.project.cms.util.ConsoleColors;

public class ConsolePrinter {

    public static void success(String message) {
        System.out.println(ConsoleColors.GREEN_BOLD + "✔ " + message + ConsoleColors.RESET);
    }

    public static void error(String message) {
        System.out.println(ConsoleColors.RED_BOLD + "✘ " + message + ConsoleColors.RESET);
    }

    public static void warning(String message) {
        System.out.println(ConsoleColors.YELLOW_BOLD + "! " + message + ConsoleColors.RESET);
    }

    public static void info(String message) {
        System.out.println(ConsoleColors.WHITE + message + ConsoleColors.RESET);
    }

    public static void system(String message) {
        System.out.println(ConsoleColors.ORANGE + message + ConsoleColors.RESET);
    }

    

    public static void headline(String title) {
        String line = "============================================";
        System.out.println();
        System.out.println(ConsoleColors.BLUE_BOLD + line + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + "   " + title.toUpperCase() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + line + ConsoleColors.RESET);
    }

    public static void subTitle(String title) {
        System.out.println(ConsoleColors.PURPLE_BOLD + "--- " + title + " ---" + ConsoleColors.RESET);
    }

    

    public static void prompt(String message) {
        System.out.print(ConsoleColors.CYAN + message + ": " + ConsoleColors.RESET);
    }

    public static void promptInline(String message) {
        System.out.print(ConsoleColors.CYAN + message + ConsoleColors.RESET);
    }

    

    public static void line() {
        System.out.println(ConsoleColors.WHITE + "--------------------------------------------" + ConsoleColors.RESET);
    }

    public static void blank() {
        System.out.println();
    }

    public static void spacing(int lines) {
        for (int i = 0; i < lines; i++) System.out.println();
    }

    

    public static void menuOption(int number, String text) {
        System.out.println(ConsoleColors.CYAN_BOLD + number + ") " + ConsoleColors.WHITE + text + ConsoleColors.RESET);
    }

    public static void menuExit() {
        System.out.println(ConsoleColors.CYAN_BOLD + "0) " + ConsoleColors.WHITE + "Logout" + ConsoleColors.RESET);
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback if command execution fails
            spacing(50);
        }
    }

    public static void printContactList(java.util.List<com.project.cms.model.Contact> contacts) {
        if (contacts.isEmpty()) {
            info("No contacts to display.");
            return;
        }

        java.util.List<String> headers = java.util.List.of(
            "ID", "First Name", "Middle Name", "Last Name", "Nickname", 
            "City", "Phone 1", "Phone 2", "Email", "LinkedIn", "Birth Date"
        );
        java.util.List<java.util.List<String>> rows = new java.util.ArrayList<>();

        for (com.project.cms.model.Contact c : contacts) {
            rows.add(java.util.List.of(
                String.valueOf(c.getContactId()),
                c.getFirstName(),
                c.getMiddleName() == null ? "" : c.getMiddleName(),
                c.getLastName(),
                c.getNickname() == null ? "" : c.getNickname(),
                c.getCity() == null ? "" : c.getCity(),
                c.getPhonePrimary(),
                c.getPhoneSecondary() == null ? "" : c.getPhoneSecondary(),
                c.getEmail() == null ? "" : c.getEmail(),
                c.getLinkedinUrl() == null ? "" : c.getLinkedinUrl(),
                c.getBirthDate() == null ? "" : c.getBirthDate().toString()
            ));
        }

        com.project.cms.util.ConsoleTable.printTable(headers, rows);
    }

    public static void printUserList(java.util.List<com.project.cms.model.User> users) {
        if (users.isEmpty()) {
            info("No users to display.");
            return;
        }

        java.util.List<String> headers = java.util.List.of(
            "ID", "Username", "Name", "Surname", "Role", "Phone", "Birth Date"
        );
        java.util.List<java.util.List<String>> rows = new java.util.ArrayList<>();

        for (com.project.cms.model.User u : users) {
            rows.add(java.util.List.of(
                String.valueOf(u.getUserId()),
                u.getUsername(),
                u.getName(),
                u.getSurname(),
                u.getRole().name(),
                u.getPhone() == null ? "" : u.getPhone(),
                u.getBirthDate() == null ? "" : u.getBirthDate().toString()
            ));
        }

        com.project.cms.util.ConsoleTable.printTable(headers, rows);
    }
}

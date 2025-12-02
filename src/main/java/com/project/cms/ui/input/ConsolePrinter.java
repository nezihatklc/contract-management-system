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
    
}




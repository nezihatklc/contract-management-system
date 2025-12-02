package com.project.cms.app;

import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UserService;
import com.project.cms.ui.menu.MainMenu;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        printAsciiArt();
        
        // Initialize Services
        UserService userService = new UserService();
        ContactService contactService = new ContactService();
        StatisticsService statisticsService = new StatisticsService();
        Scanner scanner = new Scanner(System.in);

        // Start Main Menu
        MainMenu mainMenu = new MainMenu(userService, contactService, statisticsService, scanner);
        mainMenu.show();
        
        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void printAsciiArt() {
        System.out.println("   ______            __                  __ ");
        System.out.println("  / ____/___  ____  / /Ry__  ____ ______/ /_");
        System.out.println(" / /   / __ \\/ __ \\/ __/ __ `/ ___/ ___/ __/");
        System.out.println("/ /___/ /_/ / / / / /_/ /_/ / /__/ /__/ /_  ");
        System.out.println("\\____/\\____/_/ /_/\\__/\\__,_/\\___/\\___/\\__/  ");
        System.out.println("                                            ");
    }
}

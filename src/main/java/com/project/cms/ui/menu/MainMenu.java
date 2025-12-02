package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UserService;
import java.util.Scanner;

public class MainMenu {
    
    private UserService userService;
    private ContactService contactService;
    private StatisticsService statisticsService;
    private Scanner scanner;
    
    public MainMenu(UserService userService, ContactService contactService, StatisticsService statisticsService, Scanner scanner) {
        this.userService = userService;
        this.contactService = contactService;
        this.statisticsService = statisticsService;
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("Welcome to Contract Management System");

        while (true) {
            System.out.println("\n=== LOGIN ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = userService.login(username, password);

            if (user != null) {
                System.out.println("Login successful! Welcome, " + user.getName());
                routeToMenu(user);
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    private void routeToMenu(User user) {
        switch (user.getRole()) {
            case MANAGER:
                new ManagerMenu(contactService, userService, statisticsService, scanner, user).show();
                break;
            case SENIOR_DEV:
                new SeniorDevMenu(contactService, scanner, user).show();
                break;
            case JUNIOR_DEV:
                new JuniorDevMenu(contactService, scanner, user).show();
                break;
            case TESTER:
                new TesterMenu(contactService, scanner, user).show();
                break;
            default:
                System.out.println("No menu assigned for this role.");
        }
    }
}

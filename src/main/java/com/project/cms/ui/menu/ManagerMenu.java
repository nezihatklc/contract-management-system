package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UserService;
import java.util.List;
import java.util.Scanner;

import com.project.cms.model.User;

public class ManagerMenu {
    
    private ContactService contactService;
    private UserService userService;
    private StatisticsService statisticsService;
    private Scanner scanner;
    private User user;

    public ManagerMenu(ContactService contactService, UserService userService, StatisticsService statisticsService, Scanner scanner, User user) {
        this.contactService = contactService;
        this.userService = userService;
        this.statisticsService = statisticsService;
        this.scanner = scanner;
        this.user = user;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== MANAGER MENU (" + user.getName() + " " + user.getSurname() + ") ===");
            System.out.println("1. List All Contacts");
            System.out.println("2. Search/Sort Contacts");
            System.out.println("3. Add/Update/Delete Contact");
            System.out.println("4. List All Users");
            System.out.println("5. View Statistics");
            System.out.println("6. Undo Last Operation");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            switch (choice) {
                case 1: listContacts(); break;
                case 2: new SeniorDevMenu(contactService, scanner, user).show(); break; // Reuse Senior menu for advanced ops
                case 3: new SeniorDevMenu(contactService, scanner, user).show(); break;
                case 4: listUsers(); break;
                case 5: viewStatistics(); break;
                case 6: contactService.undoLast(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void listContacts() {
        contactService.getAllContacts().forEach(System.out::println);
    }

    private void listUsers() {
        System.out.println("\n--- System Users ---");
        userService.getAllUsers().forEach(u -> 
            System.out.println(u.getUserId() + ": " + u.getUsername() + " (" + u.getRole() + ")")
        );
    }

    private void viewStatistics() {
        System.out.println("\n--- System Statistics ---");
        System.out.println("Total Users: " + statisticsService.getTotalUserCount());
        System.out.println("Total Contacts: " + statisticsService.getTotalContactCount());
        
        statisticsService.printUserCountByRole();
        statisticsService.printContactCountByCity();
        statisticsService.printLinkedinStats();
        statisticsService.printAgeStats();
    }
}

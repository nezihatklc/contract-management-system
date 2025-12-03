package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;

public class ManagerMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;
    private final StatisticsService statisticsService;

   
    public ManagerMenu(User user, ContactService contactService, UserService userService, StatisticsService statisticsService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
        this.statisticsService = statisticsService;
    }

    public void start() {
        while (true) {
            ConsolePrinter.headline("MANAGER MENU (" + user.getName() + ")");
            ConsolePrinter.menuOption(1, "Contact Operations (Senior Menu)");
            ConsolePrinter.menuOption(2, "List All Users");
            ConsolePrinter.menuOption(3, "View Statistics");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
               
                case 1 -> new SeniorDevMenu(user, contactService, userService).start();
                case 2 -> listUsers();
                case 3 -> viewStatistics();
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    private void listUsers() {
      
        // userService.getAllUsers().forEach(u -> System.out.println(u.getUsername() + " - " + u.getRole()));
        System.out.println("User listing feature coming soon...");
    }

   private void viewStatistics() {
        try {
            ConsolePrinter.subTitle("System Statistics");
            
            // If this method of StatisticsService is not ready, temporarily commented it out:
            // System.out.println("Total Contacts: " + statisticsService.getTotalContactCount(user));
            
            // safe message until implementation is ready
            System.out.println("İstatistik servisi şu an bakımda...");
            
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }
}
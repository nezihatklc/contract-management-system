package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.model.RoleType;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.Validator;

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
            ConsolePrinter.menuOption(3, "Add New User");
            ConsolePrinter.menuOption(4, "Delete User");
            ConsolePrinter.menuOption(5, "View Statistics");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> new SeniorDevMenu(user, contactService, userService).start();
                case 2 -> listUsers();
                case 3 -> addUser();
                case 4 -> deleteUser();
                case 5 -> viewStatistics();
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    private void listUsers() {
        System.out.println("\n--- System Users ---");
        //If getAllUsers is not present in the UserService, it will throw an error; if it is, it will work.
       // If you receive an error, comment this data.
// userService.getAllUsers().forEach(u -> System.out.println(u.getUsername()));
        System.out.println("User listing logic here...");
    }

    private void addUser() {
        try {
            ConsolePrinter.subTitle("Add New User");
            User newUser = new User();
            newUser.setUsername(InputHandler.readString("Username", true));
            newUser.setPasswordHash(InputHandler.readString("Password", true)); 
            newUser.setName(InputHandler.readString("First Name", true));
            newUser.setSurname(InputHandler.readString("Last Name", true));
            newUser.setPhone(InputHandler.readString("Phone", true));
            
            System.out.println("Select Role: 1.TESTER 2.JUNIOR 3.SENIOR 4.MANAGER");
            int roleChoice = InputHandler.readInt("Role ID");
            switch(roleChoice) {
                case 1 -> newUser.setRole(RoleType.TESTER);
                case 2 -> newUser.setRole(RoleType.JUNIOR_DEVELOPER);
                case 3 -> newUser.setRole(RoleType.SENIOR_DEVELOPER);
                case 4 -> newUser.setRole(RoleType.MANAGER);
                default -> { ConsolePrinter.error("Invalid role."); return; }
            }

            Validator.validateUser(newUser);
            userService.createUser(newUser, user); // user = manager (performing user)
            ConsolePrinter.success("User added successfully.");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }

    private void deleteUser() {
        ConsolePrinter.subTitle("Delete User");
        int id = InputHandler.readInt("Enter User ID to delete");

        if (id == user.getUserId()) {
            ConsolePrinter.error("SECURITY ALERT: You cannot delete your own account!");
            return;
        }

        try {
            
            userService.deleteUser(id, user); 
            ConsolePrinter.success("User deleted successfully.");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }

    private void viewStatistics() {
        try {
            ConsolePrinter.subTitle("System Statistics");
            // StatisticsService metods
           
            System.out.println("Statistics module is under maintenance...");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }
}
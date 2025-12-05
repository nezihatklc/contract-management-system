package com.project.cms.ui.menu;

import com.project.cms.model.RoleType;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UndoService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.DateUtils;
import java.time.LocalDate;
import java.util.List;

public class ManagerMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;
    private final StatisticsService statisticsService;
    private final UndoService undoService;

   
    public ManagerMenu(User user, ContactService contactService, UserService userService, UndoService undoService , StatisticsService statisticsService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
        this.statisticsService = statisticsService;
        this.undoService = undoService;
    }

     public void start() {
        while (true) {
            ConsolePrinter.headline("MANAGER MENU (" + user.getName() + ")");

            ConsolePrinter.menuOption(1, "View Contact Statistics");
            ConsolePrinter.menuOption(2, "User Management");
            ConsolePrinter.menuOption(3, "Change Password");
            ConsolePrinter.menuOption(4, "Undo Last Operation");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice", 0, 4);

            switch (choice) {
                case 1 -> viewStatistics();
                case 2 -> handleUserManagement();
                case 3 -> changePassword();
                case 4 -> undoLastAction();
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    // --- USER MANAGEMENT SUB-MENU ---
    private void handleUserManagement() {
        boolean back = false;
        while (!back) {
            ConsolePrinter.subTitle("User Management");
            ConsolePrinter.menuOption(1, "List All Users");
            ConsolePrinter.menuOption(2, "Add New User");
            ConsolePrinter.menuOption(3, "Update User");
            ConsolePrinter.menuOption(4, "Delete User");
            ConsolePrinter.menuOption(0, "Back to Main Menu");

            int choice = InputHandler.readInt("User Op", 0, 4);

            switch (choice) {
                case 1 -> listUsers();
                case 2 -> addUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 0 -> back = true;
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    private void listUsers() {
        ConsolePrinter.subTitle("All System Users");
        List<User> users = userService.getAllUsers();
        ConsolePrinter.printUserList(users);
    }

    private void addUser() {
        try {
            ConsolePrinter.subTitle("Add New User");
            User newUser = new User();
            newUser.setUsername(InputHandler.readString("Username", true));
            newUser.setPlainPassword(InputHandler.readPassword("Password"));
            newUser.setName(InputHandler.readString("First Name", true));
            newUser.setSurname(InputHandler.readString("Last Name", true));
            newUser.setPhone(InputHandler.readString("Phone", true));
            
           
            String dob = InputHandler.readString("Birth Date (dd/MM/yyyy)", false);
            if (!dob.isEmpty()) {
                LocalDate date = DateUtils.stringToDate(dob);
                if (date != null) newUser.setBirthDate(date);
                else ConsolePrinter.error("Invalid date format. Skipped.");
            }

            // Rol Seçimi
            System.out.println("Select Role: 1.Tester 2.Junior 3.Senior 4.Manager");
            int roleChoice = InputHandler.readInt("Role", 1, 4);
            switch (roleChoice) {
                case 1 -> newUser.setRole(RoleType.TESTER);
                case 2 -> newUser.setRole(RoleType.JUNIOR_DEVELOPER);
                case 3 -> newUser.setRole(RoleType.SENIOR_DEVELOPER);
                case 4 -> newUser.setRole(RoleType.MANAGER);
                default -> {
                    ConsolePrinter.error("Invalid role. Operation cancelled.");
                    return;
                }
            }

           
            userService.createUser(newUser, user);
            ConsolePrinter.success("User added successfully: " + newUser.getUsername());

        } catch (Exception e) {
            ConsolePrinter.error("Failed to add user: " + e.getMessage());
        }
    }

    private void updateUser() {
        ConsolePrinter.info("Update User feature coming soon...");
       }

    private void deleteUser() {
        int id = InputHandler.readInt("Enter User ID to delete");
        try {
            userService.deleteUser(id, user);
            ConsolePrinter.success("User deleted successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Delete failed: " + e.getMessage());
        }
    }

   
    private void viewStatistics() {
        try {
            statisticsService.showStatistics();
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }


    private void changePassword() {
        ConsolePrinter.subTitle("Change Password");
        String oldPass = InputHandler.readPassword("Old Password");
        String newPass = InputHandler.readPassword("New Password");

        try {
            userService.changePassword(user.getUserId(), oldPass, newPass);
            ConsolePrinter.success("Password changed successfully!");
        } catch (Exception e) {
            ConsolePrinter.error("Failed to change password: " + e.getMessage());
        }
    }

    private void undoLastAction() {
        try {
            undoService.undo(user);
            ConsolePrinter.success("Last operation undone successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Undo failed: " + e.getMessage());
        }
    }

}
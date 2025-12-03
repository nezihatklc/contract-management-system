package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.model.RoleType;

import com.project.cms.service.UserService;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UndoService;


import com.project.cms.ui.input.InputHandler;
import com.project.cms.ui.input.ConsolePrinter;

public class MainMenu {

    private final UserService userService;
    private final ContactService contactService;
    private final StatisticsService statisticsService;
    private final UndoService undoService;


    public MainMenu(UserService userService,
                    ContactService contactService,
                    StatisticsService statisticsService, 
                    UndoService undoService) {

        this.userService = userService;
        this.contactService = contactService;
        this.statisticsService = statisticsService;
        this.undoService = undoService;
    }

    
    public void start() {

        while (true) {

            ConsolePrinter.spacing(1);
            ConsolePrinter.headline("Login to System");
            ConsolePrinter.subTitle("Please enter your user information:");

            String username = InputHandler.readString("Username", true);
            String password = InputHandler.readPassword("Password");

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                ConsolePrinter.error("Fields cannot be empty.");
                continue;
            }

            User loggedInUser;

            try {
                loggedInUser = userService.login(username, password);
            } catch (Exception e) {
                ConsolePrinter.error(e.getMessage());
                continue;
            }

            if (loggedInUser == null) {
                ConsolePrinter.error("Invalid username or password.");
                continue;
            }

            ConsolePrinter.success(
                    "Login Successful! Welcome, "
                    + loggedInUser.getName() + " " + loggedInUser.getSurname()
            );

            redirectToRoleMenu(loggedInUser);
        }
    }

    private void redirectToRoleMenu(User user) {

        RoleType role = user.getRole();

        ConsolePrinter.spacing(1);
        ConsolePrinter.subTitle("Role: " + role.name());

        switch (role) {

            case TESTER:
                new TesterMenu(
                        user,
                        contactService,
                        userService,
                        undoService,
                ).start();
                break;

            case JUNIOR_DEVELOPER:
                new JuniorDevMenu(
                        user,
                        contactService,
                        userService,
                        undoService
                ).start();
                break;

            case SENIOR_DEVELOPER:
                new SeniorDevMenu(
                        user,
                        contactService,
                        userService,
                        undoService
                ).start();
                break;

            case MANAGER:
                new ManagerMenu(
                        user,
                        contactService,
                        userService,
                        statisticsService,
                        undoService
                ).start();
                break;

            default:
                ConsolePrinter.error("Undefined role. Returning to login screen.");
        }
    }
}

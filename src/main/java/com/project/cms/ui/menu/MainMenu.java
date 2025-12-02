package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.model.RoleType;
import com.project.cms.service.UserService;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;

import com.project.cms.ui.input.InputHandler;
import com.project.cms.ui.input.ConsolePrinter;

public class MainMenu {

    private final UserService userService;
    private final ContactService contactService;
    private final StatisticsService statisticsService;

    public MainMenu(UserService userService,
                    ContactService contactService,
                    StatisticsService statisticsService) {

        this.userService = userService;
        this.contactService = contactService;
        this.statisticsService = statisticsService;
    }

    public void start() {

        boolean running = true;

        while (running) {

            ConsolePrinter.spacing(1);
            ConsolePrinter.headline("Login to System");
            ConsolePrinter.subTitle("Please enter your user information:");

            ConsolePrinter.menuOption(0, "Exit Application");
            ConsolePrinter.menuOption(-1, "");

            // ===== USERNAME INPUT =====
            String username = InputHandler.readString("Username", true);

            // EXIT OPTION
            if (username.equals("0")) {
                running = false;
                ConsolePrinter.info("Exiting the application...");
                break;
            }

            if (username.trim().isEmpty()) {
                ConsolePrinter.error("Input cannot be empty.");
                continue;
            }

            // ===== PASSWORD INPUT =====
            String password = InputHandler.readPassword("Password");

            if (password == null || password.trim().isEmpty()) {
                ConsolePrinter.error("Input cannot be empty.");
                continue;
            }

            // ===== LOGIN PROCESS =====
            User loggedInUser = null;

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
                            + loggedInUser.getName()
                            + " "
                            + loggedInUser.getSurname()
            );

            redirectToRoleMenu(loggedInUser);
        }
    }

    // ============================================
    // ===== ROLE REDIRECTION SECTION ============
    // ============================================

    private void redirectToRoleMenu(User user) {

        RoleType role = user.getRole();

        ConsolePrinter.spacing(1);
        ConsolePrinter.subTitle("Role: " + role.name());

        switch (role) {

            case TESTER:
                new TesterMenu(user, contactService, userService).start();
                break;

            case JUNIOR_DEVELOPER:
                new JuniorDevMenu(user, contactService, userService).start();
                break;

            case SENIOR_DEVELOPER:
                new SeniorDevMenu(user, contactService, userService).start();
                break;

            case MANAGER:
                new ManagerMenu(user, contactService, userService, statisticsService).start();
                break;

            default:
                ConsolePrinter.error("Undefined role. Returning to login screen.");
        }
    }
}

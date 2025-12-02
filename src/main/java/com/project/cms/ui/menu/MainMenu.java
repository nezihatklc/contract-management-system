package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.model.Role;
import com.project.cms.service.UserService;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.util.ValidationMessages;

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
            ConsolePrinter.headline("Login to system ");
            ConsolePrinter.subTitle("Please enter your user informations: ");

            ConsolePrinter.menuOption(0, "Exit Application ");
            ConsolePrinter.menuOption(-1, ""); 

            String username = InputHandler.readString("Username", true);

            // Exit option
            if (username.equals("0")) {
                running = false;
                ConsolePrinter.info("Exiting the application...");
                break;
            }

            // Basic username validation
            if (username.trim().isEmpty()) {
                ConsolePrinter.error(ValidationMessages.INPUT_REQUIRED);
                continue;
            }

            String password = InputHandler.readPassword("Password");

            User loggedInUser = null;

            try {
                loggedInUser = userService.login(username, password);
            } catch (Exception e) {
                ConsolePrinter.error(e.getMessage());
                continue;
            }

            if (loggedInUser == null) {
                ConsolePrinter.error(ValidationMessages.USER_CREDENTIALS_INVALID);
                continue;
            }

            ConsolePrinter.success("Login Successful, Welcome, " 
                                    + loggedInUser.getName() 
                                    + " " 
                                    + loggedInUser.getSurname());

            redirectToRoleMenu(loggedInUser);
        }
    }

    private void redirectToRoleMenu(User user) {

        Role role = user.getRole();
        ConsolePrinter.spacing(1);
        ConsolePrinter.subTitle("Role: " + role.name());

        switch (role) {

            case TESTER:
                new TesterMenu(user, contactService, userService).start();
                break;

            case JUNIOR_DEV:
                new JuniorDevMenu(user, contactService, userService).start();
                break;

            case SENIOR_DEV:
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

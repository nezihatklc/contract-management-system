package com.project.cms.ui.menu;

import com.project.cms.model.RoleType;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.StatisticsService;
import com.project.cms.service.UndoService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
/**
 * The main entry point for the application's UI.
 * <p>
 * This class handles the initial user login process and redirects the authenticated user
 * to the appropriate role-specific menu (e.g., ManagerMenu, TesterMenu).
 * It acts as the central hub for navigating through the application.
 */
public class MainMenu {

    private final UserService userService;
    private final ContactService contactService;
    private final StatisticsService statisticsService;
    private final UndoService undoService;

/**
     * Constructs the MainMenu with all required services.
     *
     * @param userService       Service for user authentication and management.
     * @param contactService    Service for contact operations.
     * @param statisticsService Service for statistical data.
     * @param undoService       Service for undo operations.
     */
    public MainMenu(UserService userService,
                    ContactService contactService,
                    StatisticsService statisticsService, 
                    UndoService undoService) {

        this.userService = userService;
        this.contactService = contactService;
        this.statisticsService = statisticsService;
        this.undoService = undoService;
    }
/**
     * Starts the main application loop.
     * <p>
     * Displays the login screen, accepts credentials, and attempts to authenticate the user.
     * On successful login, it redirects to the role-specific menu.
     */
    
    public void start() {

        while (true) {
            ConsolePrinter.clearScreen();
            ConsolePrinter.spacing(1);
            ConsolePrinter.headline("Logın to System");
            ConsolePrinter.subTitle("Please enter your user information:");

            String username = InputHandler.readString("Username", true);
            String password = InputHandler.readPassword("Password");

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                ConsolePrinter.error("Fields cannot be empty.");
                InputHandler.WaitEnter();
                continue;
            }

            User loggedInUser;

            try {
                loggedInUser = userService.login(username, password);
            } catch (Exception e) {
                ConsolePrinter.error(e.getMessage());
                InputHandler.WaitEnter();
                continue;
            }

            if (loggedInUser == null) {
                ConsolePrinter.error("Invalid username or password.");
                InputHandler.WaitEnter();
                continue;
            }

            ConsolePrinter.success(
                    "Login Successful! Welcome, "
                    + loggedInUser.getName() + " " + loggedInUser.getSurname()
            );

            redirectToRoleMenu(loggedInUser);

            String choice = InputHandler.readString("Do you want to login again? (y/n)", true);
            if (!choice.equalsIgnoreCase("y")) {
                return;
            }
        }
    }
/**
     * Redirects the logged-in user to the menu corresponding to their role.
     *
     * @param user The authenticated user object.
     */
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
                        undoService
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
                        undoService,
                        statisticsService
                        
                ).start();
                break;

            default:
                ConsolePrinter.error("Undefined role. Returning to login screen.");
        }
    }
}

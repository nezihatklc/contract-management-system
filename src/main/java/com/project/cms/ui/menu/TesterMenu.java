package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UndoService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import java.util.List;
/**
 * Menu interface for the Tester role.
 * <p>
 * Testers have restricted, read-only access to the system:
 * <ul>
 * <li><b>List:</b> Can view all contacts.</li>
 * <li><b>Search:</b> Can search contacts using multiple criteria.</li>
 * <li><b>Sort:</b> Can sort contacts by various fields.</li>
 * <li><b>Restricted:</b> Cannot Add, Update, Delete contacts or perform Undo operations.</li>
 * </ul>
 */
public class TesterMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;
    private final UndoService undoService;
/**
     * Constructs a new TesterMenu with required services.
     *
     * @param user           The currently logged-in Tester.
     * @param contactService Service for handling read-only contact operations.
     * @param userService    Service for handling user operations (password change only).
     */
    public TesterMenu(User user, ContactService contactService, UserService userService, UndoService undoService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
        this.undoService = undoService;
    }
/**
     * Starts the Tester menu loop.
     * Displays available read-only options and processes user input until logout.
     */
    public void start() {
        while (true) {
            ConsolePrinter.clearScreen();
            ConsolePrinter.headline("TESTER MENU (" + user.getName() + " " + user.getSurname() + ")");
            
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Change Password");
            ConsolePrinter.menuOption(5, "Undo Last Operation");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice", 0, 5);

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                case 4 -> changePassword();
                case 5 -> undoLastAction();
                case 0 -> { 
                    ConsolePrinter.info("Logging out...");
                    return; 
                }
                default -> ConsolePrinter.error("Invalid choice. Please try again.");
            }
            InputHandler.WaitEnter();
        }
    }
/**
     * Lists all contacts available in the system.
     */
    private void listContacts() {
        ConsolePrinter.subTitle("All Contacts");
        
        List<Contact> contacts = contactService.getAllContacts();
        ConsolePrinter.printContactList(contacts);
    }
/**
     * Performs a multi-field search on contacts based on user input.
     */
    private void searchContacts() {
        ConsolePrinter.subTitle("Search Contacts");
        SearchCriteria criteria = new SearchCriteria();

        System.out.println("Enter search values (leave empty to skip):");

        String fn = InputHandler.readString("First Name", false);
        if (!fn.isEmpty()) criteria.add("first_name", fn);

        String ln = InputHandler.readString("Last Name", false);
        if (!ln.isEmpty()) criteria.add("last_name", ln);

        String nick = InputHandler.readString("Nickname", false);
        if (!nick.isEmpty()) criteria.add("nickname", nick);

        String city = InputHandler.readString("City", false);
        if (!city.isEmpty()) criteria.add("city", city);

        String email = InputHandler.readString("Email", false);
        if (!email.isEmpty()) criteria.add("email", email);

        String phone1 = InputHandler.readString("Phone (Primary)", false);
        if (!phone1.isEmpty()) criteria.add("phone_primary", phone1);

        String phone2 = InputHandler.readString("Phone (Secondary)", false);
        if (!phone2.isEmpty()) criteria.add("phone_secondary", phone2);

        String linkedin = InputHandler.readString("LinkedIn URL", false);
        if (!linkedin.isEmpty()) criteria.add("linkedin_url", linkedin);

        if (!criteria.hasCriteria()) {
            ConsolePrinter.error("No search criteria provided.");
            return;
        }

        try {
            List<Contact> results = contactService.searchContacts(criteria, user);
            
            if (results.isEmpty()) {
                ConsolePrinter.info("No matching contacts found.");
            } else {
                ConsolePrinter.success(results.size() + " contact(s) found:");
                ConsolePrinter.printContactList(results);
            }
        } catch (Exception e) {
            ConsolePrinter.error("Search failed: " + e.getMessage());
        }
    }
/**
     * Sorts contacts based on a selected field and order (Ascending/Descending).
     */
    private void sortContacts() {
        ConsolePrinter.subTitle("Sort Contacts");
        
        System.out.println("Select field to sort by:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Nickname");
        System.out.println("4. City");
        System.out.println("5. Phone (primary)");
        System.out.println("6. Email");
        System.out.println("7. Birth Date");

        
        int fieldChoice = InputHandler.readInt("Field", 1, 8);
        String field = switch (fieldChoice) {
            case 1 -> "first_name";
            case 2 -> "last_name";
            case 3 -> "nickname";
            case 4 -> "city";
            case 5 -> "phone_primary";
            case 6 -> "email";
            case 7 -> "birth_date";
            default -> "first_name";
        };


        System.out.println("Select order:");
        System.out.println("1. Ascending (A-Z)");
        System.out.println("2. Descending (Z-A)");
        
        boolean isAscending = InputHandler.readInt("Order", 1, 2) == 1;

        try {
            List<Contact> sortedList = contactService.sortContacts(field, isAscending);
            ConsolePrinter.printContactList(sortedList);
        } catch (Exception e) {
            ConsolePrinter.error("Sort failed: " + e.getMessage());
        }
    }

    private void undoLastAction() {
        ConsolePrinter.subTitle("Undo Last Operation");
        try {
            undoService.undo(user);
            ConsolePrinter.success("Last operation undone successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Undo failed: " + e.getMessage());
        }
    }
/**
     * Allows the Tester to change their own password.
     */
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
}
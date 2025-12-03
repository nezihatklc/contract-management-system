package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import java.util.List;

public class TesterMenu {
    
    
    protected final User user;
    protected final ContactService contactService;
    protected final UserService userService;

    public TesterMenu(User user, ContactService contactService, UserService userService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
    }

    public void start() {
        while (true) {
            ConsolePrinter.headline("TESTER MENU (" + user.getName() + " " + user.getSurname() + ")");
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Change Password");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                case 4 -> changePassword();
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }


    protected void listContacts() {
      
        contactService.getAllContacts().forEach(System.out::println);
    }

    protected void searchContacts() {
        SearchCriteria criteria = new SearchCriteria();
        ConsolePrinter.subTitle("Search Contacts");
        
        String fn = InputHandler.readString("First Name (Empty to skip)", false);
        if (!fn.isEmpty()) criteria.add("first_name", fn);
        
        String ln = InputHandler.readString("Last Name (Empty to skip)", false);
        if (!ln.isEmpty()) criteria.add("last_name", ln);

        
        
        try {
            
           List<Contact> results = contactService.searchContacts(fn);
            if (results.isEmpty()) {
                ConsolePrinter.info("No contacts found.");
            } else {
                results.forEach(System.out::println);
            }
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }
    
    protected void sortContacts() {
        String field = InputHandler.readString("Sort by field (first_name/last_name/city)", true);
        String orderInput = InputHandler.readString("Sort order (asc/desc)", true);
        boolean ascending = orderInput.trim().equalsIgnoreCase("asc");
        
        try {
            List<Contact> results = contactService.sortContacts(field, ascending);
            results.forEach(System.out::println);
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }

    protected void changePassword() {
        try {
            String oldPass = InputHandler.readPassword("Old Password");
            String newPass = InputHandler.readPassword("New Password");
            userService.changePassword(user.getUserId(), oldPass, newPass);
            ConsolePrinter.success("Password changed successfully.");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }
}
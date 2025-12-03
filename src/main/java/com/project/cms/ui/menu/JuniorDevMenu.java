package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.Validator;
import java.util.List;

public class JuniorDevMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;

    public JuniorDevMenu(User user, ContactService contactService, UserService userService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
    }

    public void start() {
        while (true) {
            ConsolePrinter.headline("JUNIOR DEV MENU (" + user.getName() + " " + user.getSurname() + ")");
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Update Contact");
            ConsolePrinter.menuOption(5, "Undo Last Operation");
            ConsolePrinter.menuOption(6, "Change Password");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                case 4 -> updateContact();
                case 5 -> undoLastAction();
                case 6 -> changePassword();
                case 0 -> { 
                    ConsolePrinter.info("Logging out...");
                    return; 
                }
                default -> ConsolePrinter.error("Invalid choice. Please try again.");
            }
        }
    }

    private void listContacts() {
        ConsolePrinter.subTitle("All Contacts");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            ConsolePrinter.info("No contacts found.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    private void searchContacts() {
        ConsolePrinter.subTitle("Search Contacts");
        SearchCriteria criteria = new SearchCriteria();

        System.out.println("Enter search values (leave empty to skip):");
        
        String fn = InputHandler.readString("First Name", false);
        if (!fn.isEmpty()) criteria.add("first_name", fn);

        String ln = InputHandler.readString("Last Name", false);
        if (!ln.isEmpty()) criteria.add("last_name", ln);

        String phone = InputHandler.readString("Phone", false);
        if (!phone.isEmpty()) criteria.add("phone_primary", phone);

        if (!criteria.hasCriteria()) {
            ConsolePrinter.error("No search criteria provided.");
            return;
        }

        try {
            List<Contact> results = contactService.searchAdvanced(criteria, null, true);
            if (results.isEmpty()) {
                ConsolePrinter.info("No matching contacts found.");
            } else {
                ConsolePrinter.success(results.size() + " contact(s) found:");
                results.forEach(System.out::println);
            }
        } catch (Exception e) {
            ConsolePrinter.error("Search failed: " + e.getMessage());
        }
    }

    private void sortContacts() {
        ConsolePrinter.subTitle("Sort Contacts");
        System.out.println("Select field to sort by:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Phone");
        
        int fieldChoice = InputHandler.readInt("Field");
        String field = switch (fieldChoice) {
            case 1 -> "first_name";
            case 2 -> "last_name";
            case 3 -> "phone_primary";
            default -> "first_name";
        };

        System.out.println("Select order:");
        System.out.println("1. Ascending (A-Z)");
        System.out.println("2. Descending (Z-A)");
        
        boolean isAscending = InputHandler.readInt("Order") == 1;

        try {
            List<Contact> sortedList = contactService.searchAdvanced(null, field, isAscending);
            sortedList.forEach(System.out::println);
        } catch (Exception e) {
            ConsolePrinter.error("Sort failed: " + e.getMessage());
        }
    }

    private void updateContact() {
        ConsolePrinter.subTitle("Update Contact");
        int id = InputHandler.readInt("Enter Contact ID to update");

        try {
            Contact contact = contactService.getContactById(id);
            if (contact == null) {
                ConsolePrinter.error("Contact not found.");
                return;
            }

            System.out.println("Updating: " + contact);
            System.out.println("(Press Enter to keep current value)");
            
            String newPhone = InputHandler.readString("New Phone (" + contact.getPhonePrimary() + ")", false);
            if (!newPhone.isEmpty()) {
                Validator.validatePhone(newPhone);
                contact.setPhonePrimary(newPhone);
            }

            String newEmail = InputHandler.readString("New Email (" + contact.getEmail() + ")", false);
            if (!newEmail.isEmpty()) {
                Validator.validateEmail(newEmail);
                contact.setEmail(newEmail);
            }
            
            contactService.updateContact(contact, user);
            ConsolePrinter.success("Contact updated successfully.");
            
        } catch (Exception e) {
            ConsolePrinter.error("Update failed: " + e.getMessage());
        }
    }

    private void undoLastAction() {
        try {
            contactService.undoLast();
            ConsolePrinter.success("Last operation undone successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Undo failed: " + e.getMessage());
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
}
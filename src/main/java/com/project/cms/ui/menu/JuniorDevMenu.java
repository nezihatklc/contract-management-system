package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.Validator;
import com.project.cms.exception.AppExceptions.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

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
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                case 4 -> updateContact();
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    private void listContacts() {
        List<Contact> all = contactService.getAllContacts();
        List<Contact> userContacts = all.stream()
            .filter(c -> c.getUserId() == user.getUserId())
            .collect(Collectors.toList());
        
        if (userContacts.isEmpty()) {
            ConsolePrinter.info("No contacts found.");
        } else {
            userContacts.forEach(System.out::println);
        }
    }

    private void searchContacts() {
        String firstName = InputHandler.readString("First Name (leave empty to skip)", false);
        String lastName = InputHandler.readString("Last Name (leave empty to skip)", false);
        String city = InputHandler.readString("City (leave empty to skip)", false);

        List<Contact> all = contactService.getAllContacts();
        List<Contact> results = all.stream()
            .filter(c -> c.getUserId() == user.getUserId())
            .filter(c -> firstName.isEmpty() || c.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
            .filter(c -> lastName.isEmpty() || c.getLastName().toLowerCase().contains(lastName.toLowerCase()))
            .filter(c -> city.isEmpty() || c.getCity().toLowerCase().contains(city.toLowerCase()))
            .collect(Collectors.toList());
        
        if (results.isEmpty()) {
            ConsolePrinter.info("No contacts found matching your criteria.");
        } else {
            results.forEach(System.out::println);
        }
    }
    
    private void sortContacts() {
   
        ConsolePrinter.info("Sort feature coming soon...");
    }

    private void updateContact() {
        int id = InputHandler.readInt("Enter Contact ID to update");
        try {
            Contact contact = contactService.getContactById(id);
            if (contact == null) {
                ConsolePrinter.error("Contact not found.");
                return;
            }
            
            String phone = InputHandler.readString("New Phone (Enter to keep " + contact.getPhonePrimary() + ")", false);
            if (!phone.isEmpty()) {
                Validator.validatePhone(phone);
                contact.setPhonePrimary(phone);
            }
            
            contactService.updateContact(contact, user);
            ConsolePrinter.success("Contact updated successfully.");
            
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }
}
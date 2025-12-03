package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.model.Contact;
import com.project.cms.service.ContactService;
import com.project.cms.service.UndoService;
import com.project.cms.service.UndoServiceImpl;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import java.util.List;
import java.util.stream.Collectors;

public class TesterMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UndoService undoService;

    public TesterMenu(User user, ContactService contactService) {
        this.user = user;
        this.contactService = contactService;
        this.undoService = new UndoServiceImpl();
    }

    public void start() {
        while (true) {
            ConsolePrinter.headline("TESTER MENU (" + user.getName() + ")");
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
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
        String field = InputHandler.readString("Sort by field (first_name/last_name/city)", true);
        String orderInput = InputHandler.readString("Sort order (asc/desc)", true);
        boolean ascending = orderInput.trim().equalsIgnoreCase("asc");

        try {
            List<Contact> sorted = contactService.sortContacts(field, ascending);
            List<Contact> userContacts = sorted.stream()
                .filter(c -> c.getUserId() == user.getUserId())
                .collect(Collectors.toList());
            
            if (userContacts.isEmpty()) {
                ConsolePrinter.info("No contacts to display.");
            } else {
                userContacts.forEach(System.out::println);
            }
        } catch (Exception e) {
            ConsolePrinter.error("Sort error: " + e.getMessage());
        }
    }

    private void undoLast() {
        try {
            undoService.undo(user);
            ConsolePrinter.success("Undo operation completed successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Undo error: " + e.getMessage());
        }
    }
}
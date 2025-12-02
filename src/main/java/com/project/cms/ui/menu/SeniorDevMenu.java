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

public class SeniorDevMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;

    public SeniorDevMenu(User user, ContactService contactService, UserService userService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
    }

    public void start() {
        while (true) {
            ConsolePrinter.headline("SENIOR DEV MENU (" + user.getName() + ")");
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Add New Contact");
            ConsolePrinter.menuOption(5, "Update Contact");
            ConsolePrinter.menuOption(6, "Delete Contact");
           
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> System.out.println("Sort feature coming soon...");
                case 4 -> addContact();
                case 5 -> updateContact(); // Junior menüsündeki gibi implemente edilebilir
                case 6 -> deleteContact();
                // case 7 -> contactService.undoLast(user); 
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    private void listContacts() {
        contactService.getContactsByUser(user.getUserId()).forEach(System.out::println);
    }

    private void searchContacts() {
        String keyword = InputHandler.readString("Enter search keyword", true);
        List<Contact> results = contactService.searchContacts(user.getUserId(), keyword);
        results.forEach(System.out::println);
    }

    private void addContact() {
        try {
            Contact contact = new Contact();
            contact.setFirstName(InputHandler.readString("First Name", true));
            contact.setLastName(InputHandler.readString("Last Name", true));
            contact.setPhonePrimary(InputHandler.readString("Phone", true));
            contact.setEmail(InputHandler.readString("Email", false));
            contact.setCity(InputHandler.readString("City", false));
            
            Validator.validateContact(contact);
            contactService.createContact(contact, user);
            ConsolePrinter.success("Contact added successfully.");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }

    private void updateContact() {
        // JuniorDevMenu'deki mantığın aynısı
        System.out.println("Update logic here..."); 
    }

    private void deleteContact() {
        int id = InputHandler.readInt("Enter Contact ID to delete");
        try {
            contactService.deleteContact(id, user);
            ConsolePrinter.success("Contact deleted successfully.");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }
}
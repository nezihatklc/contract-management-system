package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.Validator;


public class SeniorDevMenu extends JuniorDevMenu {

    public SeniorDevMenu(User user, ContactService contactService, UserService userService) {
        super(user, contactService, userService); 
    }

    @Override
    public void start() {
        while (true) {
            ConsolePrinter.headline("SENIOR DEV MENU (" + user.getName() + ")");
            
            
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Update Contact");
            
            
            ConsolePrinter.menuOption(5, "Add New Contact");
            ConsolePrinter.menuOption(6, "Delete Contact");
            ConsolePrinter.menuOption(7, "Undo Last Operation");
            
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                // Bu metodlar JuniorDevMenu'den miras alındı (Tekrar yazmadık!)
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                case 4 -> updateContact();
                
                // Bu metodları aşağıda yeni yazdık
                case 5 -> addContact();
                case 6 -> deleteContact();
                case 7 -> undoLastOperation();
                
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    // --- SADECE SENIOR'A ÖZEL YENİ METODLAR ---

    private void addContact() {
        try {
            ConsolePrinter.subTitle("Add New Contact");
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

    private void deleteContact() {
        int id = InputHandler.readInt("Enter Contact ID to delete");
        try {
            contactService.deleteContact(id, user);
            ConsolePrinter.success("Contact deleted successfully.");
        } catch (Exception e) {
            ConsolePrinter.error(e.getMessage());
        }
    }

    private void undoLastOperation() {
        try {
            contactService.undoLast(user); 
            ConsolePrinter.success("Last operation undone successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Undo failed: " + e.getMessage());
        }
    }
}
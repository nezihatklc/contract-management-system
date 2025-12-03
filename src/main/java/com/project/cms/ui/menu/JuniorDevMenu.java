package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.Validator;


public class JuniorDevMenu extends TesterMenu {

   

    public JuniorDevMenu(User user, ContactService contactService, UserService userService) {
        super(user, contactService, userService); // DÜZELTME 2: Babasının (Tester) kurucusunu çağırır
    }

    @Override
    public void start() {
        while (true) {
            ConsolePrinter.headline("JUNIOR DEV MENU (" + user.getName() + ")");
            
            // TesterDevMenu
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            
            // Junior first time additions
            ConsolePrinter.menuOption(4, "Update Contact"); 
            
            ConsolePrinter.menuOption(5, "Change Password");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
               
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                
                
                case 4 -> updateContact();
                
                
                case 5 -> changePassword(); 
                
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

   

    protected void updateContact() {
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
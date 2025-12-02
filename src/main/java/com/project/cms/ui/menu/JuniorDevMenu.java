package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import java.util.List;

public class JuniorDevMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;

    // MainMenu ile uyumlu Constructor
    public JuniorDevMenu(User user, ContactService contactService, UserService userService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
    }

    // Metod ismi "start" yapıldı
    public void start() {
        while (true) {
            ConsolePrinter.headline("JUNIOR DEV MENU (" + user.getName() + ")");
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Update Contact");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice");

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> System.out.println("Search özelliği eklenecek..."); // Kodları buraya taşıyabilirsin
                case 3 -> System.out.println("Sort özelliği eklenecek...");
                case 4 -> System.out.println("Update özelliği eklenecek...");
                case 0 -> { return; }
                default -> ConsolePrinter.error("Invalid choice.");
            }
        }
    }

    private void listContacts() {
        List<Contact> contacts = contactService.getAllContacts(); // Service metoduna göre düzenle
        contacts.forEach(System.out::println);
    }
}
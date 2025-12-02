package com.project.cms.ui.menu;

import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;

public class TesterMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;

    public TesterMenu(User user, ContactService contactService, UserService userService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
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
        contactService.getContactsByUser(user.getUserId()).forEach(System.out::println);
    }

    private void searchContacts() {
        String keyword = InputHandler.readString("Enter search keyword", true);
        contactService.searchContacts(user.getUserId(), keyword).forEach(System.out::println);
    }
}
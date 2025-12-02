package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.service.ContactService;
import com.project.cms.util.Validator;
import com.project.cms.exception.ValidationException;
import java.util.List;
import java.util.Scanner;

import com.project.cms.model.User;

public class SeniorDevMenu {
    
    private ContactService contactService;
    private Scanner scanner;
    private User user;

    public SeniorDevMenu(ContactService contactService, Scanner scanner, User user) {
        this.contactService = contactService;
        this.scanner = scanner;
        this.user = user;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== SENIOR DEV MENU (" + user.getName() + " " + user.getSurname() + ") ===");
            System.out.println("1. List All Contacts");
            System.out.println("2. Search Contacts");
            System.out.println("3. Sort Contacts");
            System.out.println("4. Add New Contact");
            System.out.println("5. Update Contact");
            System.out.println("6. Delete Contact");
            System.out.println("7. Undo Last Operation");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            switch (choice) {
                case 1: listContacts(); break;
                case 2: searchContacts(); break;
                case 3: sortContacts(); break;
                case 4: addContact(); break;
                case 5: updateContact(); break;
                case 6: deleteContact(); break;
                case 7: contactService.undoLast(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void listContacts() {
        contactService.getAllContacts().forEach(System.out::println);
    }

    private void searchContacts() {
        SearchCriteria criteria = new SearchCriteria();
        System.out.println("Enter search criteria (leave empty to skip):");
        System.out.print("First Name: ");
        String fn = scanner.nextLine();
        if (!fn.isEmpty()) criteria.add("first_name", fn);
        
        List<Contact> results = contactService.searchAdvanced(criteria, null, true);
        results.forEach(System.out::println);
    }
    
    private void sortContacts() {
        System.out.println("Sort by: 1. Name 2. Surname");
        String field = scanner.nextLine().equals("2") ? "last_name" : "first_name";
        List<Contact> results = contactService.searchAdvanced(null, field, true);
        results.forEach(System.out::println);
    }

    private void addContact() {
        try {
            Contact contact = new Contact();
            System.out.print("First Name: ");
            contact.setFirstName(scanner.nextLine());
            System.out.print("Last Name: ");
            contact.setLastName(scanner.nextLine());
            System.out.print("Phone: ");
            contact.setPhone(scanner.nextLine());
            System.out.print("Email: ");
            contact.setEmail(scanner.nextLine());
            System.out.print("City: ");
            contact.setCity(scanner.nextLine());
            
            Validator.validateContact(contact);
            contactService.add(contact);
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private void updateContact() {
        System.out.print("Enter Contact ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Contact contact = contactService.getById(id);
            if (contact == null) {
                System.out.println("Contact not found.");
                return;
            }
            
            System.out.println("Updating " + contact.getFirstName() + " " + contact.getLastName());
            System.out.print("New Phone (Enter to keep " + contact.getPhone() + "): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                try {
                    Validator.validatePhone(phone);
                    contact.setPhone(phone);
                } catch (ValidationException e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            }
            contactService.update(contact);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void deleteContact() {
        System.out.print("Enter Contact ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            contactService.delete(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }
}

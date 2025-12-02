package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.service.ContactService;
import java.util.List;
import java.util.Scanner;

import com.project.cms.model.User;

public class TesterMenu {
    
    private ContactService contactService;
    private Scanner scanner;
    private User user;

    public TesterMenu(ContactService contactService, Scanner scanner, User user) {
        this.contactService = contactService;
        this.scanner = scanner;
        this.user = user;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== TESTER MENU (" + user.getName() + " " + user.getSurname() + ") ===");
            System.out.println("1. List All Contacts");
            System.out.println("2. Search by Field(s)");
            System.out.println("3. Sort Contacts");
            System.out.println("4. Run Automated Tests (Mock)");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    listContacts();
                    break;
                case 2:
                    searchContacts();
                    break;
                case 3:
                    sortContacts();
                    break;
                case 4:
                    runTests();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
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
        
        System.out.print("Last Name: ");
        String ln = scanner.nextLine();
        if (!ln.isEmpty()) criteria.add("last_name", ln);
        
        System.out.print("Phone: ");
        String ph = scanner.nextLine();
        if (!ph.isEmpty()) criteria.add("phone_primary", ph);
        
        List<Contact> results = contactService.searchAdvanced(criteria, null, true);
        results.forEach(System.out::println);
    }
    
    private void sortContacts() {
        System.out.println("Sort by: 1. Name 2. Surname 3. Date");
        String field = "first_name";
        String input = scanner.nextLine();
        if (input.equals("2")) field = "last_name";
        if (input.equals("3")) field = "birth_date";
        
        System.out.println("Order: 1. Ascending 2. Descending");
        boolean asc = !scanner.nextLine().equals("2");
        
        List<Contact> results = contactService.searchAdvanced(null, field, asc);
        results.forEach(System.out::println);
    }

    private void runTests() {
        System.out.println("Running tests...");
        try {
            Thread.sleep(1000);
            System.out.println("Test 1: Contact Creation... PASS");
            Thread.sleep(500);
            System.out.println("Test 2: Contact Deletion... PASS");
            Thread.sleep(500);
            System.out.println("Test 3: Search Functionality... PASS");
            System.out.println("All tests passed successfully!");
        } catch (InterruptedException e) {
            System.out.println("Tests interrupted.");
        }
    }
}

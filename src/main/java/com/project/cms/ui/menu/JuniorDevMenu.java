package com.project.cms.ui.menu;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import com.project.cms.service.ContactService;
import com.project.cms.service.UndoService;
import com.project.cms.service.UserService;
import com.project.cms.ui.input.ConsolePrinter;
import com.project.cms.ui.input.InputHandler;
import com.project.cms.util.Validator;
import java.util.List;

public class JuniorDevMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;
    private final UndoService undoService;

    public JuniorDevMenu(User user, ContactService contactService, UserService userService, UndoService undoService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
        this.undoService = undoService;
    }   

    public void start() {
        while (true) {
            ConsolePrinter.clearScreen();
            ConsolePrinter.headline("JUNIOR DEV MENU (" + user.getName() + " " + user.getSurname() + ")");
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Update Contact");
            ConsolePrinter.menuOption(5, "Undo Last Operation");
            ConsolePrinter.menuOption(6, "Change Password");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice", 0, 6);

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
            InputHandler.WaitEnter();
        }
    }

    private void listContacts() {
        ConsolePrinter.subTitle("All Contacts");
        List<Contact> contacts = contactService.getAllContacts();
        ConsolePrinter.printContactList(contacts);
    }

    private void searchContacts() {
        ConsolePrinter.subTitle("Search Contacts");
        SearchCriteria criteria = new SearchCriteria();

        System.out.println("Enter search values (leave empty to skip):");

        String fn = InputHandler.readString("First Name", false);
        if (!fn.isEmpty()) criteria.add("first_name", fn);

        String ln = InputHandler.readString("Last Name", false);
        if (!ln.isEmpty()) criteria.add("last_name", ln);

        String nick = InputHandler.readString("Nickname", false);
        if (!nick.isEmpty()) criteria.add("nickname", nick);

        String city = InputHandler.readString("City", false);
        if (!city.isEmpty()) criteria.add("city", city);

        String email = InputHandler.readString("Email", false);
        if (!email.isEmpty()) criteria.add("email", email);

        String phone1 = InputHandler.readString("Phone (Primary)", false);
        if (!phone1.isEmpty()) criteria.add("phone_primary", phone1);

        String phone2 = InputHandler.readString("Phone (Secondary)", false);
        if (!phone2.isEmpty()) criteria.add("phone_secondary", phone2);

        String linkedin = InputHandler.readString("LinkedIn URL", false);
        if (!linkedin.isEmpty()) criteria.add("linkedin_url", linkedin);


        if (!criteria.hasCriteria()) {
            ConsolePrinter.error("No search criteria provided.");
            return;
        }

        try {
            List<Contact> results = contactService.searchContacts(criteria, user);
            if (results.isEmpty()) {
                ConsolePrinter.info("No matching contacts found.");
            } else {
                ConsolePrinter.success(results.size() + " contact(s) found:");
                ConsolePrinter.printContactList(results);
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
        System.out.println("3. Nickname");
        System.out.println("4. City");
        System.out.println("5. Phone (primary)");
        System.out.println("6. Email");
        System.out.println("7. Birth Date");


        
        int fieldChoice = InputHandler.readInt("Field", 1, 8);
        String field = switch (fieldChoice) {
            case 1 -> "first_name";
            case 2 -> "last_name";
            case 3 -> "nickname";
            case 4 -> "city";
            case 5 -> "phone_primary";
            case 6 -> "email";
            case 7 -> "birth_date";
            default -> "first_name";
        };

        System.out.println("Select order:");
        System.out.println("1. Ascending (A-Z)");
        System.out.println("2. Descending (Z-A)");
        
        boolean isAscending = InputHandler.readInt("Order", 1, 2) == 1;

        try {
            List<Contact> sortedList = contactService.sortContacts(field, isAscending);
            ConsolePrinter.printContactList(sortedList);
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

            System.out.println("Updating Contact:");
            ConsolePrinter.printContactList(List.of(contact));
            System.out.println("(Press Enter to keep current value)");
            
            String fName = InputHandler.readString("First Name (" + contact.getFirstName() + ")", false);
            if (!fName.isEmpty()) contact.setFirstName(fName);

            String mName = InputHandler.readString("Middle Name (" + (contact.getMiddleName() == null ? "" : contact.getMiddleName()) + ")", false);
            if (!mName.isEmpty()) contact.setMiddleName(mName);

            String lName = InputHandler.readString("Last Name (" + contact.getLastName() + ")", false);
            if (!lName.isEmpty()) contact.setLastName(lName);

            String nick = InputHandler.readString("Nickname (" + (contact.getNickname() == null ? "" : contact.getNickname()) + ")", false);
            if (!nick.isEmpty()) contact.setNickname(nick);

            String city = InputHandler.readString("City (" + (contact.getCity() == null ? "" : contact.getCity()) + ")", false);
            if (!city.isEmpty()) contact.setCity(city);

            String newPhone = InputHandler.readString("New Phone 1 (" + contact.getPhonePrimary() + ")", false);
            if (!newPhone.isEmpty()) {
                Validator.validatePhone(newPhone);
                contact.setPhonePrimary(newPhone);
            }

            String newPhone2 = InputHandler.readString("New Phone 2 (" + (contact.getPhoneSecondary() == null ? "" : contact.getPhoneSecondary()) + ")", false);
            if (!newPhone2.isEmpty()) {
                // Consider strict validation for secondary phone as well, or at least format check if not empty
                Validator.validatePhone(newPhone2);
                contact.setPhoneSecondary(newPhone2);
            }

            String newEmail = InputHandler.readString("New Email (" + (contact.getEmail() == null ? "" : contact.getEmail()) + ")", false);
            if (!newEmail.isEmpty()) {
                Validator.validateEmail(newEmail);
                contact.setEmail(newEmail);
            }

            String linkedin = InputHandler.readString("LinkedIn (" + (contact.getLinkedinUrl() == null ? "" : contact.getLinkedinUrl()) + ")", false);
            if (!linkedin.isEmpty()) contact.setLinkedinUrl(linkedin);

            String dob = InputHandler.readString("Birth Date (dd/MM/yyyy) (" + (contact.getBirthDate() != null ? com.project.cms.util.DateUtils.dateToString(contact.getBirthDate()) : "") + ")", false);
            if (!dob.isEmpty()) {
                java.time.LocalDate date = com.project.cms.util.DateUtils.stringToDate(dob);
                if (date != null) contact.setBirthDate(date);
                else ConsolePrinter.error("Invalid date format. Skipped.");
            }
            
            contactService.updateContact(contact, user);
            ConsolePrinter.success("Contact updated successfully.");
            
        } catch (Exception e) {
            ConsolePrinter.error("Update failed: " + e.getMessage());
        }
    }

    private void undoLastAction() {
        try {
            undoService.undo(user);
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
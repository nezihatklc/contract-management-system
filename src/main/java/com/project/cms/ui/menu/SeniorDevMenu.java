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
/**
 * Menu interface for the Senior Developer role.
 * <p>
 * Senior Developers have advanced permissions including:
 * <ul>
 * <li>All Junior capabilities (Read, Update, Undo, Password Change).</li>
 * <li><b>Create:</b> Can add new contacts to the system.</li>
 * <li><b>Delete:</b> Can remove existing contacts.</li>
 * <li><b>Undo:</b> Can undo create, update, and delete operations.</li>
 * </ul>
 */
public class SeniorDevMenu {
    
    private final User user;
    private final ContactService contactService;
    private final UserService userService;
    private final UndoService undoService;
/**
     * Constructs a new SeniorDevMenu with all required services.
     *
     * @param user           The currently logged-in Senior Developer.
     * @param contactService Service for handling contact operations.
     * @param userService    Service for handling user operations.
     * @param undoService    Service for handling undo operations.
     */
    public SeniorDevMenu(User user, ContactService contactService, UserService userService, UndoService undoService) {
        this.user = user;
        this.contactService = contactService;
        this.userService = userService;
        this.undoService = undoService;
    }
/**
     * Starts the Senior Developer menu loop.
     * Displays advanced options and processes user input until logout.
     */
    public void start() {
        while (true) {
            ConsolePrinter.clearScreen();
            ConsolePrinter.headline("SENIOR DEV MENU (" + user.getName() + " " + user.getSurname() + ")");
            
            ConsolePrinter.menuOption(1, "List All Contacts");
            ConsolePrinter.menuOption(2, "Search Contacts");
            ConsolePrinter.menuOption(3, "Sort Contacts");
            ConsolePrinter.menuOption(4, "Add New Contact");
            ConsolePrinter.menuOption(5, "Update Contact");
            ConsolePrinter.menuOption(6, "Delete Contact");
            ConsolePrinter.menuOption(7, "Undo Last Operation");
            ConsolePrinter.menuOption(8, "Change Password");
            ConsolePrinter.menuOption(0, "Logout");

            int choice = InputHandler.readInt("Choice", 0, 8);

            switch (choice) {
                case 1 -> listContacts();
                case 2 -> searchContacts();
                case 3 -> sortContacts();
                case 4 -> addContact();
                case 5 -> updateContact();
                case 6 -> deleteContact();
                case 7 -> undoLastAction();
                case 8 -> changePassword();
                case 0 -> { 
                    ConsolePrinter.info("Logging out...");
                    return; 
                }
                default -> ConsolePrinter.error("Invalid choice. Please try again.");
            }
            InputHandler.WaitEnter();
        }
    }
/**
     * Lists all contacts available in the system.
     */
    private void listContacts() {
        ConsolePrinter.subTitle("All Contacts");
        List<Contact> contacts = contactService.getAllContacts();
        ConsolePrinter.printContactList(contacts);
    }
/**
     * Performs a multi-field search on contacts.
     */
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
/**
     * Sorts contacts based on a selected field and order.
     */
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
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        
        boolean isAscending = InputHandler.readInt("Order", 1, 2) == 1;

        try {
            List<Contact> sortedList = contactService.sortContacts(field, isAscending);
            ConsolePrinter.printContactList(sortedList);
        } catch (Exception e) {
            ConsolePrinter.error("Sort failed: " + e.getMessage());
        }
    }
/**
     * Adds a new contact to the system.
     * Validates input before saving.
     */
    private void addContact() {
        ConsolePrinter.subTitle("Add New Contact");
        try {
            Contact contact = new Contact();
            contact.setFirstName(InputHandler.readString("First Name", true));
            contact.setMiddleName(InputHandler.readString("Middle Name /optional", false));
            contact.setLastName(InputHandler.readString("Last Name", true));
            contact.setNickname(InputHandler.readString("Nickname", true));
            contact.setPhonePrimary(InputHandler.readString("Primary Phone (+905xxxxxxxxx)", true));
            
            String phone2 = InputHandler.readString("Secondary Phone (+905xxxxxxxxx) / optional", false);
            if (!phone2.isEmpty()) {
                Validator.validatePhone(phone2);
                contact.setPhoneSecondary(phone2);
            }

            contact.setEmail(InputHandler.readString("Email (example@mail.com)", true));
            contact.setCity(InputHandler.readString("City", true));
            contact.setLinkedinUrl(InputHandler.readString("LinkedIn URL / optional", false));
            
            String dob = InputHandler.readString("Birth Date (dd/MM/yyyy) / optional", false);
            if (!dob.isEmpty()) {
                java.time.LocalDate date = com.project.cms.util.DateUtils.stringToDate(dob);
                if (date != null) contact.setBirthDate(date);
                else {
                    ConsolePrinter.error("Invalid date format. Birth date skipped.");
                }
            }
            
            Validator.validateContact(contact);
            contactService.createContact(contact, user);
            ConsolePrinter.success("New contact added successfully.");
            
        } catch (Exception e) {
            ConsolePrinter.error("Add failed: " + e.getMessage());
        }
    }
/**
     * Updates an existing contact's information.
     */
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
/**
     * Deletes a contact from the system.
     */
    private void deleteContact() {
        ConsolePrinter.subTitle("Delete Contact");
        int id = InputHandler.readInt("Enter Contact ID to delete");
        try {
            contactService.deleteContact(id, user);
            ConsolePrinter.success("Contact deleted successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Delete failed: " + e.getMessage());
        }
    }
/**
     * Reverts the last create, update, or delete operation.
     */
    private void undoLastAction() {
        try {
            undoService.undo(user);
            ConsolePrinter.success("Last operation undone successfully.");
        } catch (Exception e) {
            ConsolePrinter.error("Undo failed: " + e.getMessage());
        }
    }
/**
     * Allows the user to change their own password.
     */
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
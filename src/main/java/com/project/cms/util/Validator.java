package com.project.cms.util;

import com.project.cms.exception.ValidationException;
import com.project.cms.model.Contact;
import com.project.cms.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Utility class for validating data integrity.
 * Ensures that Contacts and Users meet business rules before persistence.
 */
public class Validator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    // Supports formats like: +905551234567, 05551234567, 5551234567
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+90|0)?5\\d{9}$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateContact(Contact contact) throws ValidationException {
        if (contact == null) throw new ValidationException("Contact object cannot be null.");

        if (isEmpty(contact.getFirstName())) throw new ValidationException("First name cannot be empty.");
        if (isEmpty(contact.getLastName())) throw new ValidationException("Last name cannot be empty.");
        
        validatePhone(contact.getPhone());

        if (!isEmpty(contact.getEmail())) {
            validateEmail(contact.getEmail());
        }
        
        if (contact.getBirthDate() != null && contact.getBirthDate().isAfter(LocalDate.now())) {
             throw new ValidationException("Birth date cannot be in the future.");
        }
    }

    public static void validateUser(User user) throws ValidationException {
        if (user == null) throw new ValidationException("User object cannot be null.");

        if (isEmpty(user.getUsername())) throw new ValidationException("Username cannot be empty.");
        if (isEmpty(user.getName())) throw new ValidationException("Name cannot be empty.");
        if (isEmpty(user.getSurname())) throw new ValidationException("Surname cannot be empty.");
        
        if (user.getRole() == null) throw new ValidationException("User role must be selected.");
    }

    public static void validatePhone(String phone) throws ValidationException {
        if (isEmpty(phone)) throw new ValidationException("Phone number is mandatory.");
        // Remove spaces and dashes for validation
        String cleanPhone = phone.replaceAll("[\\s-]", "");
        if (!PHONE_PATTERN.matcher(cleanPhone).matches()) {
            throw new ValidationException("Invalid phone number format. Use +90555xxxxxxx or 0555xxxxxxx.");
        }
    }

    public static void validateEmail(String email) throws ValidationException {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format.");
        }
    }
    
    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return false;
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}

package com.project.cms.util;



import com.project.cms.model.Contact;
import com.project.cms.model.User;
import com.project.cms.exception.ValidationException;
import java.util.regex.Pattern;

/**
 * Utility class performing data validation operations throughout the application.
 * <p>
 * Checks whether incoming data complies with business rules (e.g., email format, mandatory fields).
 */
public class Validator {

    // Regex: Basic email format check
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    // Regex: Checks if the string contains only digits
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d+$");

    /**
     * Validates all fields of a Contact object.
     * @param contact The contact object to validate.
     * @throws ValidationException Thrown if mandatory fields are missing or formats are invalid.
     */
    public static void validateContact(Contact contact) throws ValidationException {
        if (contact == null) throw new ValidationException("Contact object cannot be null.");

        // Check mandatory fields
        if (isEmpty(contact.getFirstName())) throw new ValidationException("First name cannot be empty.");
        if (isEmpty(contact.getLastName())) throw new ValidationException("Last name cannot be empty.");
        
        // Check phone format
        validatePhone(contact.getPhonePrimary());

        // Check email format if it exists
        if (!isEmpty(contact.getEmail())) {
            validateEmail(contact.getEmail());
        }
    }

    /**
     * Validates User object (For creating new system users).
     * @param user The user object to validate.
     * @throws ValidationException Thrown if mandatory fields are missing.
     */
    public static void validateUser(User user) throws ValidationException {
        if (user == null) throw new ValidationException("User object cannot be null.");

        if (isEmpty(user.getUsername())) throw new ValidationException("Username cannot be empty.");
        if (isEmpty(user.getName())) throw new ValidationException("Name cannot be empty.");
        if (isEmpty(user.getSurname())) throw new ValidationException("Surname cannot be empty.");
        
        // Role check
        if (user.getRole() == null) throw new ValidationException("User role must be selected.");
    }

    /**
     * Validates the format of a phone number.
     * @param phone The phone number string.
     * @throws ValidationException Thrown if the phone is empty or contains non-digit characters.
     */
    public static void validatePhone(String phone) throws ValidationException {
        if (isEmpty(phone)) throw new ValidationException("Phone number is mandatory.");
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Phone number must consist of digits only.");
        }
    }

    /**
     * Validates the format of an email address.
     * @param email The email address string.
     * @throws ValidationException Thrown if the format is invalid.
     */
    public static void validateEmail(String email) throws ValidationException {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format.");
        }
    }

    // Helper method: Checks if a string is null or empty
    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}









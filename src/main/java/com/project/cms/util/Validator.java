package com.project.cms.util;


import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.Contact;
import com.project.cms.model.User;
import java.time.LocalDate;

public class Validator {

    /* ------------------------------------------------------
       CONTACT VALIDATION  
       ------------------------------------------------------ */
    public static void validateContact(Contact c) throws ValidationException {

        if (c == null)
            throw new ValidationException("Contact cannot be null.");

        if (isEmpty(c.getFirstName()))
            throw new ValidationException("First name is required.");

        if (isEmpty(c.getLastName()))
            throw new ValidationException("Last name is required.");

        if (isEmpty(c.getNickname()))
            throw new ValidationException("Nickname is required.");

        if (isEmpty(c.getCity()))
            throw new ValidationException("City is required.");

        if (isEmpty(c.getPhonePrimary()))
            throw new ValidationException("Primary phone is required.");
        validatePhone(c.getPhonePrimary());

        if (isEmpty(c.getEmail()))
            throw new ValidationException("Email is required.");
        validateEmail(c.getEmail());

        if (c.getBirthDate() != null &&
                c.getBirthDate().isAfter(LocalDate.now()))
            throw new ValidationException("Birth date cannot be in the future.");
    }

    /* ------------------------------------------------------
       USER VALIDATION  
       ------------------------------------------------------ */
    public static void validateUser(User u) throws ValidationException {

        if (u == null)
            throw new ValidationException("User cannot be null.");

        if (isEmpty(u.getUsername()))
            throw new ValidationException("Username is required.");

        // Password hash check removed to allow new user creation flow where hash is generated after validation.
        // Service layer ensures password presence.

        if (isEmpty(u.getName()))
            throw new ValidationException("Name is required.");

        if (isEmpty(u.getSurname()))
            throw new ValidationException("Surname is required.");
            
        if (isEmpty(u.getPhone()))
            throw new ValidationException("Phone is required.");
        validatePhone(u.getPhone());

        if (u.getRole() == null)
            throw new ValidationException("User role is required.");

        if (u.getBirthDate() != null &&
                u.getBirthDate().isAfter(LocalDate.now()))
            throw new ValidationException("Birth date cannot be in the future.");
    }

    /* ------------------------------------------------------
   PASSWORD VALIDATION  
   ------------------------------------------------------ */
     public static boolean isValidPassword(String password) {

    // Cannot be null or empty
         if (password == null || password.trim().isEmpty())
            return false;

    // Minimum 4 characters
         if (password.length() < 4)
            return false;

    
    return true;
}

    /* ------------------------------------------------------
       EMAIL VALIDATION 
       ------------------------------------------------------ */
    public static void validateEmail(String email) throws ValidationException {

        if (email == null)
            throw new ValidationException("Email is required.");

        int at = email.indexOf("@");

        // email: something@something
        if (at <= 0 || at == email.length() - 1)
            throw new ValidationException("Invalid email format.");
    }

    /* ------------------------------------------------------
       PHONE VALIDATION 
       ------------------------------------------------------ */
    public static void validatePhone(String phone) throws ValidationException {

        if (phone == null || phone.trim().isEmpty())
            throw new ValidationException("Phone number is required.");

        String p = phone.replaceAll("[\\s-]", "");

        // Only allow +90 followed by 10 digits
        // +90 5xxxxxxxxx -> 13 chars total
        if (p.startsWith("+90") && p.length() == 13 && p.matches("^\\+90[0-9]{10}$")) {
             return;
        }

        throw new ValidationException("Invalid phone number format. Must start with +90 and be 13 characters long.");
    }

    /* ------------------------------------------------------
       HELPER
       ------------------------------------------------------ */
    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}

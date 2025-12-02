package com.project.cms.util;

import com.project.cms.model.Contact;
import com.project.cms.exception.ValidationException;

public class ValidatorTest {
    public static void main(String[] args) {
        System.out.println("Running Validator Tests...");
        
        testPhoneValidation();
        testContactValidation();
        
        System.out.println("All tests finished.");
    }

    private static void testPhoneValidation() {
        String[] validPhones = {"05551234567", "+905551234567"};
        String[] invalidPhones = {"123", "abc", "5551234567"}; // Missing 0 prefix

        for (String phone : validPhones) {
            try {
                Validator.validatePhone(phone);
                System.out.println("PASS: Valid phone " + phone);
            } catch (ValidationException e) {
                System.out.println("FAIL: Valid phone rejected " + phone);
            }
        }

        for (String phone : invalidPhones) {
            try {
                Validator.validatePhone(phone);
                System.out.println("FAIL: Invalid phone accepted " + phone);
            } catch (ValidationException e) {
                System.out.println("PASS: Invalid phone rejected " + phone);
            }
        }
    }

    private static void testContactValidation() {
        Contact c = new Contact();
        // Empty contact
        try {
            Validator.validateContact(c);
            System.out.println("FAIL: Empty contact accepted");
        } catch (ValidationException e) {
            System.out.println("PASS: Empty contact rejected");
        }
        
        // Valid contact
        c.setFirstName("Test");
        c.setLastName("User");
        c.setPhone("05551234567");
        try {
            Validator.validateContact(c);
            System.out.println("PASS: Valid contact accepted");
        } catch (ValidationException e) {
            System.out.println("FAIL: Valid contact rejected - " + e.getMessage());
        }
    }
}

package com.project.cms.exception;

/**
 * Custom exception class thrown when data validation operations fail.
 * <p>
 * By extending the Exception class, we define this as a checked exception
 * that must be handled when business rules (like email format) are violated.
 */
public class ValidationException extends Exception {
    
    public ValidationException(String message) {
        super(message);
    }
}
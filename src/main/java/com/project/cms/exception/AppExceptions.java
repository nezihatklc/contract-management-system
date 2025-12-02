package com.project.cms.exception;


public class AppExceptions {

    /* This file collects all custom exceptions */
  
    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class ContactNotFoundException extends Exception {
        public ContactNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    public static class UndoOperationException extends Exception {
        public UndoOperationException(String message) {
            super(message);
        }
    }
}

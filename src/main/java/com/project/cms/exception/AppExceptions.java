package com.project.cms.exception;
/**
 * A container class holding all custom exception types used throughout the
 * Contact Management System project. <br><br>
 *
 * Each nested exception represents a specific error category defined in the
 * project requirements, such as validation errors, authentication failures,
 * missing records, access restrictions, and undo mechanism failures.
 *
 * <p>This design centralizes custom exceptions for easier maintenance and
 * clearer project architecture.</p>
 * @author Simay Mutlu
 */

public class AppExceptions {

    /**
     * Thrown when any user or contact input violates required validation rules.
     * <p>Examples include:
     * <ul>
     *   <li>Invalid phone number format</li>
     *   <li>Incorrect date formats or logically impossible dates</li>
     *   <li>Missing required field values</li>
     * </ul>
     * This exception is typically used by Validator classes or input
     * processing layers.
     */

    public static class ValidationException extends Exception {
        /**
         * Creates a new ValidationException with a descriptive message.
         *
         * @param message detailed explanation of the validation error
         */
        public ValidationException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when a requested user cannot be found in the database.
     * <p>Typical cases:
     * <ul>
     *   <li>A login attempt with a username that does not exist</li>
     *   <li>A manager attempting to update/delete a user that is not present</li>
     * </ul>
     */
    public static class UserNotFoundException extends Exception {
        /**
         * Creates a new UserNotFoundException.
         *
         * @param message explanation of which user was not found
         */
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when a requested contact record cannot be found in the database.
     * <p>Typical scenarios:
     * <ul>
     *   <li>Updating a non-existing contact</li>
     *   <li>Deleting a contact ID that does not exist</li>
     *   <li>Searching for a contact with specific criteria that yields no match</li>
     * </ul>
     */
    public static class ContactNotFoundException extends Exception {
        /**
         * Creates a new ContactNotFoundException.
         *
         * @param message explanation of what contact record was missing
         */
        public ContactNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when a login attempt fails due to incorrect credentials.
     * <p>Usually raised when:
     * <ul>
     *   <li>Username exists but password does not match (hashed comparison fails)</li>
     *   <li>User enters invalid login input repeatedly</li>
     * </ul>
     */
    public static class InvalidCredentialsException extends Exception {
        /**
         * Creates a new InvalidCredentialsException.
         *
         * @param message details about the authentication failure
         */
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    /**
     * Creates a new InvalidCredentialsException.
     *
     * @param message details about the authentication failure
     */
    public static class UndoOperationException extends Exception {
        /**
         * Creates a new InvalidCredentialsException.
         *
         * @param message details about the authentication failure
         */
        public UndoOperationException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when the logged-in user attempts to access a feature or operation
     * that is not permitted by their role.
     * <p>This supports the project's Role-Based Access Control (RBAC) system.</p>
     *
     * Example cases:
     * <ul>
     *   <li>Tester trying to delete a contact</li>
     *   <li>Junior Developer attempting to manage users</li>
     *   <li>Any role attempting an unauthorized database modification</li>
     * </ul>
     */
    public static class AccessDeniedException extends Exception {
        /**
         * Creates a new AccessDeniedException.
         *
         * @param message explanation of the blocked access attempt
         */
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}

package com.project.cms.ui.input;

public class ValidationMessages {
        public static final String INPUT_REQUIRED = "This field is required and cannot be empty.";
    public static final String INPUT_MUST_BE_NUMBER = "Input must be a valid numeric value.";
    public static final String INPUT_INVALID_CHOICE = "Invalid choice. Please enter a valid menu option.";
    public static final String INPUT_INVALID_CHARACTERS = "Input may only contain letters, numbers, and spaces.";


    public static final String LENGTH_TOO_SHORT = "Input must be at least %d characters long.";
    public static final String LENGTH_TOO_LONG = "Input may be at most %d characters long.";
    public static final String RANGE_OUT_OF_BOUNDS = "Value must be between %d and %d.";

    
    public static final String ID_NOT_FOUND = "No record found for the given ID.";
    public static final String EMAIL_INVALID = "Invalid email format. Please check and try again.";
    public static final String PHONE_INVALID = "Invalid phone number format.";
    public static final String USER_CREDENTIALS_INVALID = "Incorrect username or password.";
    public static final String USER_ALREADY_EXISTS = "This username or email is already in use.";
    public static final String USER_PERMISSION_DENIED = "You do not have permission to perform this action.";

    private ValidationMessages() {}

    
}

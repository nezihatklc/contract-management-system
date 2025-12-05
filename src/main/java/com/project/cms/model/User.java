package com.project.cms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a system user entity (e.g., Manager, Senior Developer) in the Contact Management System.
 * <p>
 * This class maps to the 'users' table in the database. It manages authentication details
 * (username, password hash) and authorization via the {@link RoleType} enum.
 * <p>
 * Unlike the {@link Contact} class, this class handles system access and permissions.
 */
public class User {

    private int userId;
    private String username;
    private String passwordHash; // Stored in database (Encrypted)
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private RoleType role;       // Determines user permissions
    private LocalDateTime createdAt;
    
    // Transient field: Used only during login/registration input, not stored in DB directly
    private String plainPassword; 

    /**
     * Default constructor for creating an empty User object.
     */
    public User() {}

    /**
     * Copy constructor.
     * Creates a new User instance with the same values as the provided User object.
     * * @param u The User object to copy.
     */
    public User(User u) {
        this.userId = u.userId;
        this.username = u.username;
        this.passwordHash = u.passwordHash;   
        this.name = u.name;
        this.surname = u.surname;
        this.phone = u.phone;
        this.birthDate = u.birthDate;
        this.role = u.role;
        this.createdAt = u.createdAt;
        // plainPassword is typically not copied for security reasons, or can be if needed
    }

    // ==========================
    //      GETTER METHODS
    // ==========================

    /**
     * Gets the unique identifier of the user.
     * @return The user ID.
     */
    public int getUserId() { return userId; }

    /**
     * Gets the username used for login.
     * @return The username string.
     */
    public String getUsername() { return username; }

    /**
     * Gets the hashed version of the password.
     * This is the value stored in the database for security.
     * @return The password hash.
     */
    public String getPasswordHash() { return passwordHash; }

    /**
     * Gets the first name of the user.
     * @return The first name.
     */
    public String getName() { return name; }

    /**
     * Gets the last name (surname) of the user.
     * @return The surname.
     */
    public String getSurname() { return surname; }

    /**
     * Gets the phone number of the user.
     * @return The phone number.
     */
    public String getPhone() { return phone; }

    /**
     * Gets the birth date of the user.
     * @return The birth date as a LocalDate.
     */
    public LocalDate getBirthDate() { return birthDate; }

    /**
     * Gets the role of the user (e.g., MANAGER, TESTER).
     * This determines the permissions within the application.
     * @return The RoleType enum.
     */
    public RoleType getRole() { return role; }

    /**
     * Gets the timestamp when the user account was created.
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Gets the raw (plain text) password.
     * <p>
     * <b>Note:</b> This is typically used only during the registration or login process
     * before the password is hashed. It is not stored in the database.
     * @return The plain text password.
     */
    public String getPlainPassword() { return plainPassword; }


    // ==========================
    //      SETTER METHODS
    // ==========================

    /**
     * Sets the unique identifier for the user.
     * @param userId The user ID.
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * Sets the username for login.
     * @param username The unique username.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Sets the hashed password.
     * This method should be called after hashing the plain password.
     * @param passwordHash The encrypted password string.
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    /**
     * Sets the first name of the user.
     * @param name The first name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the last name of the user.
     * @param surname The surname.
     */
    public void setSurname(String surname) { this.surname = surname; }

    /**
     * Sets the phone number of the user.
     * @param phone The phone number.
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Sets the birth date of the user.
     * @param birthDate The birth date.
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    /**
     * Assigns a role to the user.
     * @param role The RoleType (e.g., SENIOR_DEVELOPER).
     */
    public void setRole(RoleType role) { this.role = role; }

    /**
     * Sets the account creation timestamp.
     * @param createdAt The timestamp.
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Sets the plain text password.
     * Used for temporary storage before hashing.
     * @param plainPassword The raw password input.
     */
    public void setPlainPassword(String plainPassword) { this.plainPassword = plainPassword; }
}
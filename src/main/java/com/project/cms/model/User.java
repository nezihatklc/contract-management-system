package com.project.cms.model;



import java.time.LocalDate;

import java.time.LocalDateTime;



/**

 * Represents a system user (e.g., Manager, Developer).

 * Includes authentication details like username, password hash, and role.

 * This version DOES NOT extend Person (because DB structure does not use Person).

 */

public class User {



    private int userId;

    private String username;

    private String passwordHash;

    private String name;

    private String surname;

    private String phone;

    private LocalDate birthDate;

    private RoleType role;

    private LocalDateTime createdAt;
// Field to hold raw password input temporarily (not stored in DB)
    private String plainPassword;

/**
     * Default constructor.
     */

    public User() {}



    // === GETTER METHODS ===
/**
     * Gets the unique user ID.
     * @return The user ID.
     */
    public int getUserId() { return userId; }
/**
     * Gets the username.
     * @return The username string.
     */
    public String getUsername() { return username; }
/**
     * Gets the hashed password string.
     * @return The encrypted password.
     */
    public String getPasswordHash() { return passwordHash; }
/**
     * Gets the user's first name.
     * @return The first name.
     */
    public String getName() { return name; }
/**
     * Gets the user's last name.
     * @return The surname.
     */
    public String getSurname() { return surname; }
/**
     * Gets the user's phone number.
     * @return The phone number string.
     */
    public String getPhone() { return phone; }
/**
     * Gets the user's birth date.
     * @return The birth date as LocalDate.
     */
    public LocalDate getBirthDate() { return birthDate; }
/**
     * Gets the assigned role of the user.
     * @return The RoleType enum.
     */
    public RoleType getRole() { return role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
/**
     * Gets the plain text password (used during login/update).
     * @return The raw password.
     */
    public String getPlainPassword() { return plainPassword; }





    // === SETTER METHODS ===
/**
     * Sets the user ID.
     * @param userId The unique ID.
     */
    public void setUserId(int userId) { this.userId = userId; }
/**
     * Sets the username.
     * @param username The unique username.
     */
    public void setUsername(String username) { this.username = username; }
/**
     * Sets the hashed password.
     * @param passwordHash The encrypted string.
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
/**
     * Sets the first name.
     * @param name The first name.
     */
    public void setName(String name) { this.name = name; }
/**
     * Sets the last name.
     * @param surname The surname.
     */
    public void setSurname(String surname) { this.surname = surname; }
/**
     * Sets the phone number.
     * @param phone The phone number.
     */
    public void setPhone(String phone) { this.phone = phone; }
/**
     * Sets the birth date.
     * @param birthDate The birth date.
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
/**
     * Assigns a role to the user.
     * @param role The RoleType enum.
     */
    public void setRole(RoleType role) { this.role = role; }
/**
     * Sets the creation timestamp.
     * @param createdAt The timestamp.
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
/**
     * Sets the plain text password for processing.
     * @param plainPassword The raw password input.
     */
    public void setPlainPassword(String plainPassword) { this.plainPassword = plainPassword; }
/**
     * Copy constructor.
     * Creates a new User object by copying fields from an existing one.
     * Useful for update operations where original data needs to be preserved or modified safely.
     *
     * @param u The source User object to copy.
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

    }



}
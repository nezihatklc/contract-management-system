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

    public User() {}

    // === GETTER METHODS ===
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPhone() { return phone; }
    public LocalDate getBirthDate() { return birthDate; }
    public RoleType getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // === SETTER METHODS ===
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setRole(RoleType role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

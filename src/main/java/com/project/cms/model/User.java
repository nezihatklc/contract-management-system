package com.project.cms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a User entity in the system.
 * Compatible with UserDaoImpl methods.
 */
public class User {

    private int userId;
    private String username;
    private String passwordHash;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private Role role;
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
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // === SETTER METHODS ===
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
package com.project.cms.model;

import java.time.LocalDateTime;

/**
 * Represents a system user (e.g., Manager, Developer).
 * Extends Person to inherit common fields.
 * Includes authentication details like username, password hash, and role.
 */
public class User extends Person {

    private int userId;
    private String username;
    private String passwordHash;
    private Role role;
    private LocalDateTime createdAt;

    public User() {}

    // === GETTER METHODS ===
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    
    // Delegating to Person fields for compatibility with existing code if needed, 
    // or we can just use getFirstName/getLastName. 
    // The DB has 'name' and 'surname', so we map them here.
    public String getName() { return getFirstName(); }
    public String getSurname() { return getLastName(); }
    
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // === SETTER METHODS ===
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public void setName(String name) { setFirstName(name); }
    public void setSurname(String surname) { setLastName(surname); }
    
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
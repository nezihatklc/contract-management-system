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
<<<<<<< HEAD
    private Role role;
=======
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private RoleType role;
>>>>>>> f5718489d5185396536f59de0c762cdc48e8058d
    private LocalDateTime createdAt;

    public User() {}

    // === GETTER METHODS ===
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
<<<<<<< HEAD
    
    // Delegating to Person fields for compatibility with existing code if needed, 
    // or we can just use getFirstName/getLastName. 
    // The DB has 'name' and 'surname', so we map them here.
    public String getName() { return getFirstName(); }
    public String getSurname() { return getLastName(); }
    
    public Role getRole() { return role; }
=======
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPhone() { return phone; }
    public LocalDate getBirthDate() { return birthDate; }
    public RoleType getRole() { return role; }
>>>>>>> f5718489d5185396536f59de0c762cdc48e8058d
    public LocalDateTime getCreatedAt() { return createdAt; }

    // === SETTER METHODS ===
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
<<<<<<< HEAD
    
    public void setName(String name) { setFirstName(name); }
    public void setSurname(String surname) { setLastName(surname); }
    
    public void setRole(Role role) { this.role = role; }
=======
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setRole(RoleType role) { this.role = role; }
>>>>>>> f5718489d5185396536f59de0c762cdc48e8058d
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
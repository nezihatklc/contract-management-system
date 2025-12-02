package com.project.cms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Contact entity in the system.
 * Compatible with ContactDaoImpl methods.
 */
public class Contact {

    private int contactId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nickname;
    private String city;
    private String phonePrimary;
    private String phoneSecondary;
    private String email;
    private String linkedinUrl;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Contact() {
    }

    // === GETTER METHODS ===
    public int getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getNickname() { return nickname; }
    public String getCity() { return city; }
    public String getPhonePrimary() { return phonePrimary; }
    public String getPhoneSecondary() { return phoneSecondary; }
    public String getEmail() { return email; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public LocalDate getBirthDate() { return birthDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // === SETTER METHODS  ===
    public void setContactId(int contactId) { this.contactId = contactId; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setCity(String city) { this.city = city; }
    

    
    public void setPhoneSecondary(String phoneSecondary) { this.phoneSecondary = phoneSecondary; }
    public void setEmail(String email) { this.email = email; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Contact {" +
                "id=" + contactId +
                ", firstName='" + getFirstName() + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", nickname='" + nickname + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", phoneSecondary='" + phoneSecondary + '\'' +
                ", email='" + email + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", birthDate=" + getBirthDate() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
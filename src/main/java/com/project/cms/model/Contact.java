package com.project.cms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Contact {

    private int contactId;
    private int userId;
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

    public Contact() {}

    // === GETTERS ===
    public int getContactId() { return contactId; }
    public int getUserId() { return userId; }
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

    // === SETTERS ===
    public void setContactId(int contactId) { this.contactId = contactId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setCity(String city) { this.city = city; }
    public void setPhonePrimary(String phonePrimary) { this.phonePrimary = phonePrimary; }
    public void setPhoneSecondary(String phoneSecondary) { this.phoneSecondary = phoneSecondary; }
    public void setEmail(String email) { this.email = email; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Contact {" +
                "id=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", city='" + city + '\'' +
                ", phonePrimary='" + phonePrimary + '\'' +
                ", phoneSecondary='" + phoneSecondary + '\'' +
                ", email='" + email + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

package com.project.cms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Represents a Contact entity in the Contact Management System.
 * <p>
 * This class maps to the 'contacts' table in the database and holds all personal
 * and contact details of an individual. It functions as a Data Transfer Object (DTO)
 * between the database layer (DAO) and the business logic layer (Service).
 */
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
/**
     * Default constructor for creating an empty Contact object.
     */
    public Contact() {}

    // === GETTERS ===
    /**
     * Gets the unique identifier of the contact.
     * @return The contact ID.
     */
    public int getContactId() { return contactId; }
    /**
     * Gets the ID of the user who owns this contact record.
     * @return The owner user's ID.
     */
    public int getUserId() { return userId; }
/**
     * Gets the first name of the contact.
     * @return The first name.
     */
    public String getFirstName() { return firstName; }
   /**
     * Gets the middle name of the contact (optional).
     * @return The middle name.
     */
    public String getMiddleName() { return middleName; }
    /**
     * Gets the last name of the contact.
     * @return The last name.
     */
    public String getLastName() { return lastName; }
    /**
     * Gets the nickname or alias of the contact.
     * @return The nickname.
     */
    public String getNickname() { return nickname; }
    /**
     * Gets the city where the contact resides.
     * @return The city name.
     */
    public String getCity() { return city; }
    /**
     * Gets the primary phone number of the contact.
     * @return The primary phone number.
     */
    public String getPhonePrimary() { return phonePrimary; }
    /**
     * Gets the secondary phone number of the contact (optional).
     * @return The secondary phone number.
     */
    public String getPhoneSecondary() { return phoneSecondary; }
    /**
     * Gets the email address of the contact.
     * @return The email address.
     */
    public String getEmail() { return email; }
    /**
     * Gets the LinkedIn profile URL of the contact.
     * @return The LinkedIn URL.
     */
    public String getLinkedinUrl() { return linkedinUrl; }
    /**
     * Gets the birth date of the contact.
     * @return The birth date as a LocalDate.
     */
    public LocalDate getBirthDate() { return birthDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    /**
     * Gets the timestamp when the contact was last updated.
     * @return The last update timestamp.
     */
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // === SETTERS ===
    /**
     * Sets the unique identifier for the contact.
     * @param contactId The contact ID.
     */
    public void setContactId(int contactId) { this.contactId = contactId; }
    /**
     * Sets the ID of the user who owns this contact.
     * @param userId The user ID.
     */
    public void setUserId(int userId) { this.userId = userId; }
    /**
     * Sets the first name of the contact.
     * @param firstName The first name.
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }
    /**
     * Sets the middle name of the contact.
     * @param middleName The middle name.
     */
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    /**
     * Sets the last name of the contact.
     * @param lastName The last name.
     */
    public void setLastName(String lastName) { this.lastName = lastName; }
    /**
     * Sets the nickname of the contact.
     * @param nickname The nickname.
     */
    public void setNickname(String nickname) { this.nickname = nickname; }
    /**
     * Sets the city of the contact.
     * @param city The city name.
     */
    public void setCity(String city) { this.city = city; }
    /**
     * Sets the primary phone number.
     * @param phonePrimary The phone number string.
     */
    public void setPhonePrimary(String phonePrimary) { this.phonePrimary = phonePrimary; }
    /**
     * Sets the secondary phone number.
     * @param phoneSecondary The secondary phone number string.
     */
    public void setPhoneSecondary(String phoneSecondary) { this.phoneSecondary = phoneSecondary; }
    /**
     * Sets the email address.
     * @param email The email string.
     */
    public void setEmail(String email) { this.email = email; }
    /**
     * Sets the LinkedIn profile URL.
     * @param linkedinUrl The URL string.
     */
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    /**
     * Sets the birth date of the contact.
     * @param birthDate The birth date.
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    /**
     * Sets the creation timestamp.
     * @param createdAt The timestamp.
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    /**
     * Returns a string representation of the contact object.
     * Useful for debugging and logging purposes.
     * * @return A string containing key contact details.
     */

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

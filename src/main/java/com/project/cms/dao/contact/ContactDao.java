package com.project.cms.dao.contact;

import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import java.util.List;

/**
 * Data Access Object (DAO) interface for managing Contact entities.
 * <p>
 * This interface defines all database operations related to contacts, including:
 * CRUD operations, flexible search capabilities, sorting, and statistical queries.
 * It is implemented by {@code ContactDaoImpl} using MySQL.
 * <p>
 * All methods in this interface operate directly on the database layer and do not
 * contain business logic. Business validation must be performed in the Service layer.
 */
public interface ContactDao {

    // ===========================================================
    // CREATE
    // ===========================================================

    /**
     * Inserts a single contact record into the database.
     *
     * @param contact the contact to be added
     */
    int addContact(Contact contact);

     /**
     * Inserts multiple contact records into the database in batch mode.
     *
     * @param contacts list of contacts to be added
     */
    void addContacts(List<Contact> contacts);

    // ===========================================================
    // READ
    // ===========================================================

    /**
     * Retrieves a contact by its unique ID.
     *
     * @param id the contact ID
     * @return the matching Contact object, or {@code null} if none found
     */
    Contact findById(int id);

     /**
     * Retrieves all contacts stored in the database.
     *
     * @return list of all contacts
     */
    List<Contact> findAll();

    // ===========================================================
    // UPDATE
    // ===========================================================

    /**
     * Updates an existing contact record in the database.
     * The contact must already exist (matched by contact_id).
     *
     * @param contact the contact object containing updated fields
     */
    boolean updateContact(Contact contact);

    // ===========================================================
    // DELETE
    // ===========================================================

    /**
     * Deletes a single contact using its ID.
     *
     * @param id the ID of the contact to delete
     */
    boolean deleteContactById(int id);

    /**
     * Deletes multiple contacts based on a list of IDs.
     *
     * @param ids the list of contact IDs to delete
     */
    void deleteContactsByIds(List<Integer> ids);

    // ===========================================================
    // SEARCH
    // ===========================================================

    /**
     * Searches contacts using single or multi-field dynamic criteria.
     * Supports exact and partial (LIKE) matches.
     *
     * @param criteria the search criteria map (field → value)
     * @return list of contacts matching the criteria
     */
    List<Contact> search(SearchCriteria criteria);

    // ===========================================================
    // SORTING
    // ===========================================================

    /**
     * Retrieves all contacts sorted by a specific field.
     *
     * @param field     the column name to sort by
     * @param ascending true for ASC, false for DESC
     * @return sorted list of contacts
     */
    List<Contact> findAllSorted(String field, boolean ascending);


    //             STATISTICS METHODS (MANAGER ONLY)
    // ===========================================================

     /**
     * @return total number of contacts in the database
     */
    int countAllContacts();

    /**
     * @return number of contacts that have a LinkedIn URL
     */
    int countContactsWithLinkedin();

    /**
     * @return number of contacts without a LinkedIn URL
     */
    int countContactsWithoutLinkedin();

    /**
     * Finds the most frequently occurring first name.
     *
     * @return the most common first name
     */
    String findMostCommonFirstName();

    /**
     * Finds the most frequently occurring last name.
     *
     * @return the most common last name
     */
    String findMostCommonLastName();

    
    /**
     * @return the youngest contact based on birth_date
     */
    Contact findYoungestContact();

    /**
     * @return the oldest contact based on birth_date
     */
    Contact findOldestContact();

    /**
     * Calculates the average age of all contacts.
     *
     * @return average age as a double (years)
     */
    double getAverageAge();

    /**
     * Retrieves the distribution of contacts by city.
     *
     * @return list of string arrays: (city, count)
     */
    List<String[]> getCityDistribution();          
    /**
     * Returns the number of contacts within predefined age groups.
     * Example groups: "18-25", "26-35", etc.
     *
     * @return list of pairs: { ageGroup, count }
     */
    List<String[]> getAgeGroupDistribution();

    /**
     * Returns the most common first names and their counts.
     *
     * @return list of pairs: { firstname, count }
     */
    List<String[]> getTopFirstNames();

    /**
     * Returns the most common last names and their counts.
     *
     * @return list of pairs: { lastname, count }
     */
    List<String[]> getTopLastNames();

    /**
     * Returns contact count per birth month.
     * Example: {"January", "12"}
     *
     * @return list of pairs: { monthName, count }
     */
    List<String[]> getBirthMonthDistribution();
}

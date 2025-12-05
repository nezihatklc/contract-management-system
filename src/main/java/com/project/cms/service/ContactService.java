package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import java.util.List;

/**
 * Defines the main operations for managing contacts.
 * Role checks and undo actions are handled in the service layer.
 *
 * @author Simay
 */

public interface ContactService {

    // CREATE (Senior only)
    /**
     * 
     * @param contact - the contact to be created
     * @param performingUser - the user performing the operation
     * @return the created contact
     * @throws ValidationException - if the contact contains invalid or incomplete fields
     * @throws AccessDeniedException - if the user does not have Senior role
     */
    Contact createContact(Contact contact, User performingUser)
            throws ValidationException, AccessDeniedException;


    // RESTORE (For Undo - preserves ID)
    /**
     *  Restores a previously deleted contact during an Undo operation.
     * @param contact - the contact to restore
     * @param performingUser - the user performing the restoration
     * @return the restored Contact
     * @throws ValidationException if the contact data is invalid
     * @throws AccessDeniedException if the user is not authorized to perform this operation
     */
    Contact restoreContact(Contact contact, User performingUser)
            throws ValidationException, AccessDeniedException;


    // UPDATE (Junior + Senior)
    /**
     * Updates an existing contact.
     * @param updated - the updated contact object
     * @param performingUser - the user performing the update
     * @throws ValidationException - if updated fields are invalid
     * @throws ContactNotFoundException - if the contact does not exist
     * @throws AccessDeniedException - if the user is not allowed to update
     */
    void updateContact(Contact updated, User performingUser)
            throws ValidationException, ContactNotFoundException, AccessDeniedException;

     /**
      * Updates an existing contact with the option to disable Undo recording.
      * @param updated - the updated contact object
      * @param performingUser - the user performing the update
      * @param recordUndo - whether an undo action should be recorded
      * @throws ValidationException - if updated fields are invalid
      * @throws ContactNotFoundException - if the contact does not exist
      * @throws AccessDeniedException - if the user is not allowed to update
      */
    void updateContact(Contact updated, User performingUser, boolean recordUndo)
            throws ValidationException, ContactNotFoundException, AccessDeniedException;



    // DELETE (Senior only)
    /**
     * Deletes a contact by ID.
     * @param contactId - the ID of the contact to delete
     * @param performingUser - the user performing the deletion
     * @throws ContactNotFoundException - if the ID does not correspond to an existing contact
     * @throws AccessDeniedException - if the user cannot delete
     */
    void deleteContact(int contactId, User performingUser)
            throws ContactNotFoundException, AccessDeniedException;


     /**
      * Deletes a contact with an option to disable Undo recording.
      * @param contactId - the ID of the contact to delete
      * @param performingUser - the user performing the deletion
      * @param recordUndo - whether an undo action should be recorded
      * @throws ContactNotFoundException - if the contact is not found
      * @throws AccessDeniedException - if the user is unauthorized
      */
    void deleteContact(int contactId, User performingUser, boolean recordUndo)
            throws ContactNotFoundException, AccessDeniedException;


    // GET SINGLE CONTACT
    /**
     * Gets one contact by ID.
     * @param contactId - contactId the ID to search for
     * @return  the matching Contact
     * @throws ContactNotFoundException - if no contact exists with the given ID
     */
    Contact getContactById(int contactId)
            throws ContactNotFoundException;


    // LIST ALL CONTACTS
    /**
     * 
     * @return gets all contacts
     */
    List<Contact> getAllContacts();


    // SORT CONTACTS
    /**
     * Sorts contacts by any field in ascending or descending order.
     * @param field - the field name to sort by
     * @param ascending - true for ascending, false for descending
     * @return sorted list of contacts
     */
    List<Contact> sortContacts(String field, boolean ascending);

    // SEARCH (OR logic)
    /**
     * 
     * @param criteria - the search rules
     * @param performingUser - the user doing the search
     * @return contacts that match
     * @throws AccessDeniedException - if the user cannot search
     */
    public List<Contact> searchContacts(SearchCriteria criteria, User performingUser)
            throws AccessDeniedException;
}

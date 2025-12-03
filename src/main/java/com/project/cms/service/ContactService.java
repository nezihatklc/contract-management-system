package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.Contact;
import com.project.cms.model.SearchCriteria;
import com.project.cms.model.User;
import java.util.List;

/* Defines all business operations for managing contacts. */
/* Role checks and undo logging happen in service layer, not DAO. */

public interface ContactService {

    // CREATE (Senior only)
    Contact createContact(Contact contact, User performingUser)
            throws ValidationException, AccessDeniedException;

    // UPDATE (Junior + Senior)
    void updateContact(Contact updated, User performingUser)
            throws ValidationException, ContactNotFoundException, AccessDeniedException;

    // DELETE (Senior only)
    void deleteContact(int contactId, User performingUser)
            throws ContactNotFoundException, AccessDeniedException;

    // GET SINGLE CONTACT
    Contact getContactById(int contactId)
            throws ContactNotFoundException;

    // LIST ALL CONTACTS
    List<Contact> getAllContacts();

    // SORT CONTACTS
    List<Contact> sortContacts(String field, boolean ascending);

    // SEARCH (OR logic)
    public List<Contact> searchContacts(SearchCriteria criteria, User performingUser)
            throws AccessDeniedException;
}

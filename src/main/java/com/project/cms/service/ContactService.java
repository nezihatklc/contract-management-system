package com.project.cms.service;

import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.Contact;
import com.project.cms.model.User;

import java.util.List;

/* Defines operations for managing contacts. */
/* Create, update, delete and search operations. */
  
public interface ContactService {

    // Senior Developer only: add new contact 
    Contact createContact(Contact contact, User performingUser)
            throws ValidationException;

    // Junior + Senior: update contact 
    void updateContact(Contact contact, User performingUser)
            throws ValidationException, ContactNotFoundException;

    // Senior Developer only: delete contact 
    void deleteContact(int contactId, User performingUser)
            throws ContactNotFoundException;

    // Retrieve single contact 
    Contact getContactById(int contactId)
            throws ContactNotFoundException;

    // List all contacts belonging to a user 
    List<Contact> getContactsByUser(int userId);

    // Search contacts 
    List<Contact> searchContacts(int userId, String keyword);
}

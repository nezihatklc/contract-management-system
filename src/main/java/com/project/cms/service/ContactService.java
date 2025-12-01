package com.project.cms.service;

import com.project.cms.exception.ContactNotFoundException;
import com.project.cms.exception.ValidationException;
import com.project.cms.model.Contact;

import java.util.List;

public interface ContactService {

    Contact createContact(Contact contact)
            throws ValidationException;

    void updateContact(Contact contact)
            throws ValidationException, ContactNotFoundException;

    void deleteContact(int contactId)
            throws ContactNotFoundException;

    Contact getContactById(int contactId)
            throws ContactNotFoundException;

    List<Contact> getContactsByUser(int userId);

    List<Contact> searchContacts(int userId, String keyword);

}

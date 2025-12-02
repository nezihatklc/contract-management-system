package com.project.cms.service;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.dao.contact.ContactDaoImpl;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;

import com.project.cms.model.Contact;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;
import com.project.cms.util.Validator;

import java.util.List;

/* Handles contact operations. */
/* Allows or blocks actions based on the user's role. */
/* Saves each operation so it can be undone later. */

public class ContactServiceImpl implements ContactService {

    private final ContactDao contactDao = new ContactDaoImpl();
    private final UserService userService = new UserServiceImpl();
    private final UndoService undoService = new UndoServiceImpl();

    /* CREATE CONTACT */

    @Override
    public Contact createContact(Contact contact, User performingUser)
            throws ValidationException, AccessDeniedException {

        // Only Senior Dev allowed
        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.addNewContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can add contacts.");
        }

        // Validate whole contact
        Validator.validateContact(contact);

        // Save contact
        Contact saved = contactDao.;

        // Undo record
        UndoAction action = new UndoAction(
                UndoAction.ActionType.CREATE,
                null,
                saved
        );
        undoService.recordUndoAction(performingUser, action);

        return saved;
    }

    /* UPDATE CONTACT */

    @Override
    public void updateContact(Contact contact, User performingUser)
            throws ValidationException, ContactNotFoundException, AccessDeniedException {

        // Junior + Senior allowed
        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.updateExistingContact();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Junior/Senior can update contacts.");
        }

        Contact old = contactDao.findById(contact.getContactId());
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        // Validate updated contact
        Validator.validateContact(contact);

        // Apply update
        contactDao.updateContact(contact);;

        // Undo record
        UndoAction action = new UndoAction(
                UndoAction.ActionType.UPDATE,
                old,
                contact
        );
        undoService.recordUndoAction(performingUser, action);
    }

    /*  DELETE CONTACT */

    @Override
    public void deleteContact(int contactId, User performingUser)
            throws ContactNotFoundException, AccessDeniedException {

        // Senior allowed only
        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.deleteExistingContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can delete contacts.");
        }

        Contact old = contactDao.findById(contactId);
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        contactDao.deleteContactById(contactId);;

        // Undo record
        UndoAction action = new UndoAction(
                UndoAction.ActionType.DELETE,
                old,
                null
        );
        undoService.recordUndoAction(performingUser, action);
    }

    /* GET CONTACT */

    @Override
    public Contact getContactById(int contactId)
            throws ContactNotFoundException {

        Contact contact = contactDao.findById(contactId);
        if (contact == null)
            throw new ContactNotFoundException("Contact not found.");

        return contact;
    }

     /* LIST BY USER */

    @Override
    public List<Contact> getContactsByUser(int userId) {
        return contactDao.findByUserId(userId);
    }

    /* SEARCH */

    @Override
    public List<Contact> searchContacts(int userId, String keyword) {
        return contactDao.search(userId, keyword);;
    }
}

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
            throws ValidationException {

        // Only Senior Dev allowed
        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.addNewContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can add contacts.");
        }

        if (!Validator.isValidName(contact.getFirstName()))
            throw new ValidationException("Invalid first name.");

        Contact saved = contactDao.save(contact);

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
            throws ValidationException, ContactNotFoundException {

        // Junior + Senior allowed
        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.updateExistingContact();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Junior/Senior can update contacts.");
        }

        Contact old = contactDao.findById(contact.getId());
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        contactDao.update(contact);

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
            throws ContactNotFoundException {

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

        contactDao.delete(contactId);

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

    /*  LIST BY USER */

    @Override
    public List<Contact> getContactsByUser(int userId) {
        return contactDao.findByUserId(userId);
    }

    /* SEARCH */

    @Override
    public List<Contact> searchContacts(int userId, String keyword) {
        return contactDao.search(userId, keyword);
    }
}

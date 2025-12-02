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
import com.project.cms.model.SearchCriteria;

import java.util.List;

public class ContactServiceImpl implements ContactService {

    private final ContactDao contactDao = new ContactDaoImpl();
    private final UserService userService = new UserServiceImpl();
    private final UndoService undoService = new UndoServiceImpl();

    /* CREATE CONTACT (Senior Only) */
    @Override
    public Contact createContact(Contact contact, User performingUser)
            throws ValidationException, AccessDeniedException {

        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.addNewContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can add contacts.");
        }

        Validator.validateContact(contact);

        contactDao.addContact(contact);

        List<Contact> all = contactDao.findAll();
        Contact saved = all.get(all.size() - 1);

        UndoAction action = new UndoAction(
                UndoAction.ActionType.CREATE,
                null,
                saved
        );
        undoService.recordUndoAction(performingUser, action);

        return saved;
    }

    /* UPDATE CONTACT (Junior + Senior) */
    @Override
    public void updateContact(Contact contact, User performingUser)
            throws ValidationException, ContactNotFoundException, AccessDeniedException {

        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.updateExistingContact();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Junior/Senior can update contacts.");
        }

        Contact old = contactDao.findById(contact.getContactId());
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        Validator.validateContact(contact);

        contactDao.updateContact(contact);

        UndoAction action = new UndoAction(
                UndoAction.ActionType.UPDATE,
                old,
                contact
        );
        undoService.recordUndoAction(performingUser, action);
    }

    /* DELETE CONTACT (Senior Only) */
    @Override
    public void deleteContact(int contactId, User performingUser)
            throws ContactNotFoundException, AccessDeniedException {

        RolePermissions perm = userService.getPermissionsFor(performingUser);
        try {
            perm.deleteExistingContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can delete contacts.");
        }

        Contact old = contactDao.findById(contactId);
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        contactDao.deleteContactById(contactId);

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

        Contact c = contactDao.findById(contactId);
        if (c == null)
            throw new ContactNotFoundException("Contact not found.");

        return c;
    }

    /* LIST ALL CONTACTS  */
    @Override
    public List<Contact> getContactsByUser(int userId) {
        return contactDao.findAll();
    }

    /* SEARCH USING SearchCriteria  */
    @Override
    public List<Contact> searchContacts(int userId, String keyword) {

        SearchCriteria criteria = new SearchCriteria();
        criteria.add("first_name", keyword);
        criteria.add("last_name", keyword);
        criteria.add("city", keyword);
        criteria.add("email", keyword);

        return contactDao.search(criteria);
    }
}

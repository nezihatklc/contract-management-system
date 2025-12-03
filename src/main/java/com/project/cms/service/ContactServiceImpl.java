package com.project.cms.service;

import com.project.cms.dao.contact.ContactDao;
import com.project.cms.dao.contact.ContactDaoImpl;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;

import com.project.cms.model.Contact;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;
import com.project.cms.model.SearchCriteria;
import com.project.cms.util.Validator;

import java.util.List;

public class ContactServiceImpl implements ContactService {

    private final ContactDao contactDao = new ContactDaoImpl();
    private final UserService userService = new UserServiceImpl();
    private final UndoService undoService = new UndoServiceImpl();

    /* =========================================================
       CREATE CONTACT  (Senior Only)
    ========================================================= */
    @Override
    public Contact createContact(Contact contact, User performingUser)
            throws ValidationException, AccessDeniedException {

        // Role check
        try {
            userService.getPermissionsFor(performingUser).addNewContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can create contacts.");
        }

        // Validate
        Validator.validateContact(contact);

        // Insert → returns new ID
        int newId = contactDao.addContact(contact);
        Contact saved = contactDao.findById(newId);

        // Undo: CREATE → undo = DELETE
        undoService.recordUndoAction(
                performingUser,
                UndoAction.forContactCreate(saved)
        );

        return saved;
    }

    /* =========================================================
       UPDATE CONTACT (Junior + Senior)
    ========================================================= */
    @Override
    public void updateContact(Contact updated, User performingUser)
            throws ValidationException, ContactNotFoundException, AccessDeniedException {

        // Role check
        try {
            userService.getPermissionsFor(performingUser).updateExistingContact();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Junior/Senior can update contacts.");
        }

        // Find old contact
        Contact old = contactDao.findById(updated.getContactId());
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        // Validate new version
        Validator.validateContact(updated);

        boolean ok = contactDao.updateContact(updated);
        if (!ok)
            throw new ContactNotFoundException("Update failed.");

        // Undo: UPDATE → restore old version
        undoService.recordUndoAction(
                performingUser,
                UndoAction.forContactUpdate(old, updated)
        );
    }

    /* =========================================================
       DELETE CONTACT (Senior Only)
    ========================================================= */
    @Override
    public void deleteContact(int contactId, User performingUser)
            throws ContactNotFoundException, AccessDeniedException {

        // Role check
        try {
            userService.getPermissionsFor(performingUser).deleteExistingContactOrContacts();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Senior Developer can delete contacts.");
        }

        Contact old = contactDao.findById(contactId);
        if (old == null)
            throw new ContactNotFoundException("Contact not found.");

        boolean ok = contactDao.deleteContactById(contactId);
        if (!ok)
            throw new ContactNotFoundException("Delete failed.");

        // Undo: DELETE → recreate old contact
        undoService.recordUndoAction(
                performingUser,
                UndoAction.forContactDelete(old)
        );
    }

    /* =========================================================
       GET CONTACT BY ID
    ========================================================= */
    @Override
    public Contact getContactById(int contactId)
            throws ContactNotFoundException {

        Contact c = contactDao.findById(contactId);
        if (c == null)
            throw new ContactNotFoundException("Contact not found.");

        return c;
    }

    /* =========================================================
       GET ALL CONTACTS
    ========================================================= */
    @Override
    public List<Contact> getAllContacts() {
        return contactDao.findAll();
    }

    /* =========================================================
       SORT CONTACTS
    ========================================================= */
    @Override
    public List<Contact> sortContacts(String field, boolean ascending) {
        return contactDao.findAllSorted(field, ascending);
    }

    /* =========================================================
       SEARCH (OR LOGIC)
    ========================================================= */
    @Override
    public List<Contact> searchContacts(String keyword) {

        if (keyword == null || keyword.trim().isEmpty())
            return contactDao.findAll();

        SearchCriteria criteria = new SearchCriteria();
        criteria.add("first_name", keyword);
        criteria.add("last_name", keyword);
        criteria.add("city", keyword);
        criteria.add("email", keyword);
        criteria.add("nickname", keyword);

        return contactDao.search(criteria);
    }
}

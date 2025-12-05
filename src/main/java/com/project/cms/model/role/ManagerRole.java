package com.project.cms.model.role;

/**
 * Permission implementation for the Manager role.
 * <p>
 * Manager can perform all user-management and statistical operations.
 * Manager has NO access to contact CRUD operations.
 * @author Nezihat Kılıç
 */
public class ManagerRole implements RolePermissions {

    /* ---------------- ALLOWED OPERATIONS ---------------- */

    @Override
    public void changePassword() {
        // allowed
    }

    @Override
    public void logout() {
        // allowed
    }

    @Override
    public void showContactsStatisticalInfo() {
        // allowed
    }

    @Override
    public void listAllUsers() {
        // allowed
    }

    @Override
    public void updateExistingUser() {
        // allowed
    }

    @Override
    public void addNewUser() {
        // allowed
    }

    @Override
    public void deleteExistingUser() {
        // allowed
    }


    /* ---------------- CONTACT OPERATIONS (NOT ALLOWED) ---------------- */

    @Override
    public void listAllContacts() {
        throw new UnsupportedOperationException("Manager cannot access contact list.");
    }

    @Override
    public void searchBySingleField() {
        throw new UnsupportedOperationException("Manager cannot search contacts.");
    }

    @Override
    public void searchByMultipleFields() {
        throw new UnsupportedOperationException("Manager cannot search contacts.");
    }

    @Override
    public void sortContacts() {
        throw new UnsupportedOperationException("Manager cannot sort contacts.");
    }

    @Override
    public void updateExistingContact() {
        throw new UnsupportedOperationException("Manager cannot edit contacts.");
    }

    @Override
    public void addNewContactOrContacts() {
        throw new UnsupportedOperationException("Manager cannot add contacts.");
    }

    @Override
    public void deleteExistingContactOrContacts() {
        throw new UnsupportedOperationException("Manager cannot delete contacts.");
    }
}

package com.project.cms.model.role;

/**
 * Permission implementation for the Senior Developer role.
 * <p>
 * Senior Developer can read, update, add, and delete contacts.
 * They cannot perform any user-management or statistical operations.
 * @author Nezihat Kılıç
 */
public class SeniorDeveloperRole implements RolePermissions {

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
    public void listAllContacts() {
        // allowed
    }

    @Override
    public void searchBySingleField() {
        // allowed
    }

    @Override
    public void searchByMultipleFields() {
        // allowed
    }

    @Override
    public void sortContacts() {
        // allowed
    }

    /**
     * Senior Developer can update existing contacts.
     */
    @Override
    public void updateExistingContact() {
        // allowed
    }

    /**
     * Senior Developer can add new contacts.
     */
    @Override
    public void addNewContactOrContacts() {
        // allowed
    }

    /**
     * Senior Developer can delete existing contacts.
     */
    @Override
    public void deleteExistingContactOrContacts() {
        // allowed
    }


    /* ---------------- NOT ALLOWED OPERATIONS ---------------- */

    @Override
    public void showContactsStatisticalInfo() {
        throw new UnsupportedOperationException("Senior Developer cannot view statistics.");
    }

    @Override
    public void listAllUsers() {
        throw new UnsupportedOperationException("Senior Developer cannot list users.");
    }

    @Override
    public void updateExistingUser() {
        throw new UnsupportedOperationException("Senior Developer cannot update users.");
    }

    @Override
    public void addNewUser() {
        throw new UnsupportedOperationException("Senior Developer cannot add users.");
    }

    @Override
    public void deleteExistingUser() {
        throw new UnsupportedOperationException("Senior Developer cannot delete users.");
    }
}

package com.project.cms.model.role;

/**
 * Permission implementation for the Junior Developer role.
 * <p>
 * Junior Developer can read contact data and update existing contacts.
 * Cannot add/delete contacts or perform any user-management/statistics operations.
 * @author Nezihat Kılıç
 */
public class JuniorDeveloperRole implements RolePermissions {

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
     * Junior Developer is allowed to update existing contacts.
     */
    @Override
    public void updateExistingContact() {
        // allowed
    }


    /* ---------------- NOT ALLOWED OPERATIONS ---------------- */

    @Override
    public void addNewContactOrContacts() {
        throw new UnsupportedOperationException("Junior Developer cannot add contacts.");
    }

    @Override
    public void deleteExistingContactOrContacts() {
        throw new UnsupportedOperationException("Junior Developer cannot delete contacts.");
    }

    @Override
    public void showContactsStatisticalInfo() {
        throw new UnsupportedOperationException("Junior Developer cannot view statistics.");
    }

    @Override
    public void listAllUsers() {
        throw new UnsupportedOperationException("Junior Developer cannot list users.");
    }

    @Override
    public void updateExistingUser() {
        throw new UnsupportedOperationException("Junior Developer cannot update users.");
    }

    @Override
    public void addNewUser() {
        throw new UnsupportedOperationException("Junior Developer cannot add users.");
    }

    @Override
    public void deleteExistingUser() {
        throw new UnsupportedOperationException("Junior Developer cannot delete users.");
    }
}

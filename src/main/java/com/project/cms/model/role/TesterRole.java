package com.project.cms.model.role;

/**
 * Permission implementation for the Tester role.
 * <p>
 * Tester can only read contact data (list, search, sort) and manage own session.
 * All modification and user-management operations are prohibited.
 * @author Nezihat Kılıç
 */
public class TesterRole implements RolePermissions {

    /* ---------------- COMMON ---------------- */
    @Override
    public void changePassword() {
        // Allowed
    }

    @Override
    public void logout() {
        // Allowed
    }

    /* ---------------- CONTACT OPERATIONS (allowed) ---------------- */

    @Override
    public void listAllContacts() {
        // Allowed
    }

    @Override
    public void searchBySingleField() {
        // Allowed
    }

    @Override
    public void searchByMultipleFields() {
        // Allowed
    }

    @Override
    public void sortContacts() {
        // Allowed
    }

    /* ---------------- NOT ALLOWED ---------------- */

    @Override
    public void updateExistingContact() {
        throw new UnsupportedOperationException("Tester cannot update contacts.");
    }

    @Override
    public void addNewContactOrContacts() {
        throw new UnsupportedOperationException("Tester cannot add contacts.");
    }

    @Override
    public void deleteExistingContactOrContacts() {
        throw new UnsupportedOperationException("Tester cannot delete contacts.");
    }

    @Override
    public void showContactsStatisticalInfo() {
        throw new UnsupportedOperationException("Tester cannot view statistics.");
    }

    @Override
    public void listAllUsers() {
        throw new UnsupportedOperationException("Tester cannot list users.");
    }

    @Override
    public void updateExistingUser() {
        throw new UnsupportedOperationException("Tester cannot update users.");
    }

    @Override
    public void addNewUser() {
        throw new UnsupportedOperationException("Tester cannot add users.");
    }

    @Override
    public void deleteExistingUser() {
        throw new UnsupportedOperationException("Tester cannot delete users.");
    }
}

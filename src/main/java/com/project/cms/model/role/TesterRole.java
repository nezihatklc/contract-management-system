package com.project.cms.model.role;

public class TesterRole implements RolePermissions {

    /* ---------------- COMMON ---------------- */
    @Override
    public void changePassword() {
        // TODO: call password change service
    }

    @Override
    public void logout() {
        // TODO: handle logout
    }

    /* ---------------- CONTACT OPERATIONS (allowed) ---------------- */

    @Override
    public void listAllContacts() {
        // TODO: list contacts
    }

    @Override
    public void searchBySingleField() {
        // TODO: search with single field
    }

    @Override
    public void searchByMultipleFields() {
        // TODO: multi-field search
    }

    @Override
    public void sortContacts() {
        // TODO: sort contacts
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

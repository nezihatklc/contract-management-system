package com.project.cms.model.role;

public class JuniorDeveloperRole implements RolePermissions {

    /* ---------------- COMMON ---------------- */
    @Override
    public void changePassword() {
        // TODO
    }

    @Override
    public void logout() {
        // TODO
    }

    /* ---------------- CONTACT OPERATIONS (allowed) ---------------- */

    @Override
    public void listAllContacts() {
        // TODO
    }

    @Override
    public void searchBySingleField() {
        // TODO
    }

    @Override
    public void searchByMultipleFields() {
        // TODO
    }

    @Override
    public void sortContacts() {
        // TODO
    }

    /* ---------------- CONTACT EDIT (Junior ek izin) ---------------- */

    @Override
    public void updateExistingContact() {
        // TODO: update contact
    }

    /* ---------------- NOT ALLOWED ---------------- */

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

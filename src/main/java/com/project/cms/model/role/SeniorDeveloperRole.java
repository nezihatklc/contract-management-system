package com.project.cms.model.role;

public class SeniorDeveloperRole implements RolePermissions {

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

    /* ---------------- CONTACT EDIT (Junior + Senior) ---------------- */

    @Override
    public void updateExistingContact() {
        // TODO
    }

    /* ---------------- CONTACT CREATE/DELETE (Senior ek izin) ---------------- */

    @Override
    public void addNewContactOrContacts() {
        // TODO
    }

    @Override
    public void deleteExistingContactOrContacts() {
        // TODO
    }

    /* ---------------- NOT ALLOWED ---------------- */

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


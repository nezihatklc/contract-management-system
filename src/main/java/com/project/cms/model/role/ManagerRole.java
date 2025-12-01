package com.project.cms.model.role;

public class ManagerRole implements RolePermissions {

    /* ---------------- COMMON ---------------- */
    @Override
    public void changePassword() {
        // TODO
    }

    @Override
    public void logout() {
        // TODO
    }

    /* ---------------- USER OPERATIONS (allowed) ---------------- */

    @Override
    public void showContactsStatisticalInfo() {
        // TODO
    }

    @Override
    public void listAllUsers() {
        // TODO
    }

    @Override
    public void updateExistingUser() {
        // TODO
    }

    @Override
    public void addNewUser() {
        // TODO
    }

    @Override
    public void deleteExistingUser() {
        // TODO
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

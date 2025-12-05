package com.project.cms.model;

public class UndoAction {

    /* ====================================================
       1) TYPES OF ACTIONS THAT CAN BE UNDONE
       ==================================================== */
    public enum ActionType {
        CONTACT_CREATE,
        CONTACT_UPDATE,
        CONTACT_DELETE,
        USER_CREATE,
        USER_UPDATE,
        USER_DELETE,
        PASSWORD_CHANGE
    }

    private final ActionType type;

    // Contact için kullanılacak alanlar
    private final Contact oldContact;
    private final Contact newContact;

    // User için kullanılacak alanlar
    private final User oldUser;
    private final User newUser;

    /* ====================================================
       2) BASE CONSTRUCTOR
       ==================================================== */
    public UndoAction(ActionType type,
                      Contact oldContact,
                      Contact newContact,
                      User oldUser,
                      User newUser) {

        this.type = type;
        this.oldContact = oldContact;
        this.newContact = newContact;
        this.oldUser = oldUser;
        this.newUser = newUser;
    }

    /* ====================================================
       3) GETTERS
       ==================================================== */
    public ActionType getType() {
        return type;
    }

    public Contact getOldContact() {
        return oldContact;
    }

    public Contact getNewContact() {
        return newContact;
    }

    public User getOldUser() {
        return oldUser;
    }

    public User getNewUser() {
        return newUser;
    }

    /* ====================================================
       4) CONTACT FACTORY METHODS
       ==================================================== */

    // CONTACT → CREATE (undo = delete)
    public static UndoAction forContactCreate(Contact created) {
        return new UndoAction(
                ActionType.CONTACT_CREATE,
                null,
                created,
                null,
                null
        );
    }

    // CONTACT → UPDATE (undo = revert old)
    public static UndoAction forContactUpdate(Contact oldC, Contact newC) {
        return new UndoAction(
                ActionType.CONTACT_UPDATE,
                oldC,
                newC,
                null,
                null
        );
    }

    // CONTACT → DELETE (undo = recreate)
    public static UndoAction forContactDelete(Contact deleted) {
        return new UndoAction(
                ActionType.CONTACT_DELETE,
                deleted,
                null,
                null,
                null
        );
    }

    /* ====================================================
       5) USER FACTORY METHODS
       ==================================================== */

    // USER → CREATE (undo = delete user)
    public static UndoAction forUserCreate(User created) {
        return new UndoAction(
                ActionType.USER_CREATE,
                null,
                null,
                null,
                created
        );
    }

    // USER → UPDATE (undo = restore old user)
    public static UndoAction forUserUpdate(User oldU, User newU) {
        return new UndoAction(
                ActionType.USER_UPDATE,
                null,
                null,
                oldU,
                newU
        );
    }

    // USER → DELETE (undo = recreate user)
    public static UndoAction forUserDelete(User deleted) {
        return new UndoAction(
                ActionType.USER_DELETE,
                null,
                null,
                deleted,
                null
        );
    }

    // PASSWORD → CHANGE (undo = restore old password)
    public static UndoAction forPasswordChange(User oldU, User newU) {
        return new UndoAction(
                ActionType.PASSWORD_CHANGE,
                null,
                null,
                oldU,
                newU
        );
    }
}

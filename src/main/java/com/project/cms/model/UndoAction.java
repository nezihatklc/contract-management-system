package com.project.cms.model;
/**
 * Represents an encapsulation of an action that can be undone.
 * <p>
 * This class stores the state of an entity (Contact or User) before and after
 * a modification, allowing the system to revert changes if needed.
 * It supports undo operations for CREATE, UPDATE, and DELETE actions.
 */
public class UndoAction {

    /* ====================================================
       1) TYPES OF ACTIONS THAT CAN BE UNDONE
       ==================================================== */
       /**
     * Enum defining the types of actions that can be recorded and undone.
     */
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

    // for contact used fields
    private final Contact oldContact;
    private final Contact newContact;

    // for user used fields
    private final User oldUser;
    private final User newUser;

    /* ====================================================
       2) BASE CONSTRUCTOR
       ==================================================== */
       /**
     * Constructs a new UndoAction with the specified details.
     *
     * @param type The type of action performed.
     * @param oldContact The state of the contact before the action (if applicable).
     * @param newContact The state of the contact after the action (if applicable).
     * @param oldUser The state of the user before the action (if applicable).
     * @param newUser The state of the user after the action (if applicable).
     */
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
       /**
     * Gets the type of the action.
     * @return The ActionType.
     */
    public ActionType getType() {
        return type;
    }
/**
     * Gets the previous state of the contact.
     * @return The old Contact object.
     */
    public Contact getOldContact() {
        return oldContact;
    }
/**
     * Gets the new state of the contact.
     * @return The new Contact object.
     */
    public Contact getNewContact() {
        return newContact;
    }
/**
     * Gets the previous state of the user.
     * @return The old User object.
     */
    public User getOldUser() {
        return oldUser;
    }
/**
     * Gets the new state of the user.
     * @return The new User object.
     */
    public User getNewUser() {
        return newUser;
    }

    /* ====================================================
       4) CONTACT FACTORY METHODS
       ==================================================== */
       /**
     * Creates an undo action for a Contact creation.
     * Undo logic: Delete the created contact.
     * @param created The newly created contact.
     * @return A configured UndoAction instance.
     */

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
/**
     * Creates an undo action for a Contact update.
     * Undo logic: Revert to the old contact state.
     * @param oldC The contact before update.
     * @param newC The contact after update.
     * @return A configured UndoAction instance.
     */
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
/**
     * Creates an undo action for a Contact deletion.
     * Undo logic: Re-create the deleted contact.
     * @param deleted The contact that was deleted.
     * @return A configured UndoAction instance.
     */
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
/**
     * Creates an undo action for a User creation.
     * Undo logic: Delete the created user.
     * @param created The newly created user.
     * @return A configured UndoAction instance.
     */
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
/**
     * Creates an undo action for a User update.
     * Undo logic: Revert to the old user state.
     * @param oldU The user before update.
     * @param newU The user after update.
     * @return A configured UndoAction instance.
     */
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
/**
     * Creates an undo action for a User deletion.
     * Undo logic: Re-create the deleted user.
     * @param deleted The user that was deleted.
     * @return A configured UndoAction instance.
     */
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
/**
     * Creates an undo action for a Password change.
     * Undo logic: Revert to the old password.
     * @param oldU The user object with the old password.
     * @param newU The user object with the new password.
     * @return A configured UndoAction instance.
     */
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

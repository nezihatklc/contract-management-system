package com.project.cms.model.role;

/**
 * This interface defines ALL operations that can exist in the system.
 * Each role (Tester, Junior Dev, Senior Dev, Manager) will override
 * only the methods they are allowed to perform.
 *
 * ALL METHODS ARE EMPTY SIGNATURES — implementation is done in role classes.
 * @author Nezihat Kılıç
 */
public interface RolePermissions {

    /* -----------------------------------------------------------
       COMMON OPERATIONS (all roles)
    ------------------------------------------------------------ */
    void changePassword();
    void logout();


    /* -----------------------------------------------------------
       CONTACT OPERATIONS (Tester + Junior + Senior)
    ------------------------------------------------------------ */

    /** List all contacts (no filtering, no sorting) */
    void listAllContacts();

    /** Search by a single selected field (first_name, last_name, phone...) */
    void searchBySingleField();

    /** Search using multiple selected fields (2+ conditions) */
    void searchByMultipleFields();

    /** Sort contacts by any selected field (ASC or DESC) */
    void sortContacts();


    /* -----------------------------------------------------------
       CONTACT MODIFY OPERATIONS
       (Junior + Senior)
    ------------------------------------------------------------ */

    /** Update one existing contact */
    void updateExistingContact();


    /* -----------------------------------------------------------
       CONTACT CREATE / DELETE OPERATIONS
       (only Senior Developer)
    ------------------------------------------------------------ */

    /** Add one or multiple new contacts */
    void addNewContactOrContacts();

    /** Delete one or multiple existing contacts */
    void deleteExistingContactOrContacts();


    /* -----------------------------------------------------------
       USER OPERATIONS (only Manager)
    ------------------------------------------------------------ */

    /** Display statistical info about contacts */
    void showContactsStatisticalInfo();

    /** List all system users */
    void listAllUsers();

    /** Update an existing user (change role, name, etc.) */
    void updateExistingUser();

    /** Add/Employ a new user */
    void addNewUser();

    /** Delete/Fire an existing user */
    void deleteExistingUser();
}

package com.project.cms.dao.user;

import com.project.cms.model.User;
import java.util.List;


/**
 * Data Access Object (DAO) interface for performing database operations
 * related to the User entity.
 *
 * This interface provides methods for:
 * - Authentication (find by username)
 * - Password update
 * - Full user management for Manager (CRUD operations)
 */
public interface UserDao {

    /**
     * Retrieves a User by its username.
     * Used primarily for login authentication.
     *
     * @param username the username to search for
     * @return the User object if found, otherwise null
     */
    User findByUsername(String username);

    /**
     * Retrieves a User by its unique ID.
     * Used by Manager or service logic when editing user information.
     *
     * @param userId the ID of the user
     * @return the User object if found, otherwise null
     */
    User getUserById(int userId);

    /**
     * Returns a list of all users in the system.
     * Accessible only to Manager role.
     *
     * @return a list containing all User objects
     */
    List<User> getAllUsers();

    /**
     * Updates the password hash of the user with the specified ID.
     *
     * @param userId the ID of the user whose password will be updated
     * @param newPasswordHash the hashed password value
     */
    void updatePassword(int userId, String newPasswordHash);

    /**
     * Updates all editable fields of a User entity.
     * Manager can change: name, surname, phone, birthDate, role, etc.
     *
     * @param user the User object containing updated values
     */
    void updateUser(User user);

    /**
     * Adds a new user into the system.
     * Used by Manager for user creation.
     *
     * @param user the User object to insert into the database
     */
    void addUser(User user);

    /**
     * Deletes a user from the system.
     * Optional depending on project requirement.
     *
     * @param userId the ID of the user to delete
     */
    void deleteUser(int userId);
}
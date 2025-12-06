package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;
import java.util.List;


/**
 * Defines user-related operations such as login, password changes,
 * role permissions, and manager-level operations for creating,
 * updating, and deleting users.
 *
 * @author Simay
 */

public interface UserService {

        // LOGIN
        /**
         * Logs a user into the system using their username and password.
         * @param username
         * @param password
         * @return the logged-in user
         * @throws InvalidCredentialsException - if the password is incorrect
         * @throws UserNotFoundException - if the user does not exist
         */
        User login(String username, String password)
                throws InvalidCredentialsException, UserNotFoundException;


        // CHANGE PASSWORD
        /**
         * Changes the password of a user.
         * @param userId
         * @param oldPass - the current password
         * @param newPass - the new password to set
         * @throws ValidationException - if the new password is invalid
         * @throws InvalidCredentialsException - if the old password is incorrect
         */
        void changePassword(int userId, String oldPass, String newPass)
                throws ValidationException, InvalidCredentialsException;


        /**
         * Restores the previous password of a user.
         * Restores the previous password of a user.
         * @param userId
         * @param oldPasswordHash - the previous password hash to restore
         * @param performingUser - the user performing the undo
         * @throws UserNotFoundException - if the user does not exist
         * @throws AccessDeniedException - if the user is not allowed to restore a password
         */
        void restorePreviousPassword(int userId, String oldPasswordHash, User performingUser)
                throws UserNotFoundException, AccessDeniedException;



        // GET ROLE PERMISSIONS
        /**
         * Gets the role permissions for the given user.
         * @param user
         * @return the permissions of the user
         */
        RolePermissions getPermissionsFor(User user);


        // MANAGER: CREATE USER
        /**
         * Creates a new user. Only Managers can perform this action.
         * @param newUser - the user to create
         * @param performingUser - the user performing the operation
         * @return the created user
         * @throws ValidationException. - if the new user data is invalid
         * @throws AccessDeniedException - if the user is not allowed to create users
         */
        User createUser(User newUser, User performingUser)
                throws ValidationException, AccessDeniedException;

        /**
         * Creates a new user with optional undo recording.
         * @param newUser - the user to create
         * @param performingUser - the user performing the operation
         * @param recordUndo - whether undo should be recorded
         * @return the created user
         * @throws ValidationException - if the new user data is invalid
         * @throws AccessDeniedException - if the user is not allowed to create users
         */
        User createUser(User newUser, User performingUser, boolean recordUndo)
                throws ValidationException, AccessDeniedException;


        // MANAGER: UPDATE USER
        /**
         * Updates an existing user.
         * @param updatedUser - the updated user data
         * @param performingUser - the user performing the update
         * @throws ValidationException - if the updated data is invalid
         * @throws UserNotFoundException - if the user does not exist
         * @throws AccessDeniedException - if the user is not allowed to update users
         */
        void updateUser(User updatedUser, User performingUser)
                throws ValidationException, UserNotFoundException, AccessDeniedException;

        /**
         * Updates a user with optional undo recording.
         * @param updatedUser - the updated user
         * @param performingUser - the user performing the operation
         * @param recordUndo - recordUndo whether undo should be recorded
         * @throws ValidationException - if the updated data is invalid
         * @throws UserNotFoundException - if the user does not exist
         * @throws AccessDeniedException - if the user is not allowed to update users
         */
        void updateUser(User updatedUser, User performingUser, boolean recordUndo)
                throws ValidationException, UserNotFoundException, AccessDeniedException;


        // MANAGER: DELETE USER
        /**
         * Deletes a user.
         * @param targetUserId - the ID of the user to delete
         * @param performingUser - the user performing the deletion
         * @throws UserNotFoundException - if the user does not exist
         * @throws AccessDeniedException - if the user is not allowed to delete users
         */
        void deleteUser(int targetUserId, User performingUser)
                throws UserNotFoundException, AccessDeniedException;

        /**
         * Deletes a user with optional undo recording.
         * @param targetUserId - the ID of the user to delete
         * @param performingUser - the user performing the deletion
         * @param recordUndo - whether undo should be recorded
         * @throws UserNotFoundException - if the user does not exist
         * @throws AccessDeniedException - if the user is not allowed to delete users
         */
        void deleteUser(int targetUserId, User performingUser, boolean recordUndo)
                throws UserNotFoundException, AccessDeniedException;

        /**
         * 
         * @return Returns all users in the system.
         */
        List<User> getAllUsers();

}

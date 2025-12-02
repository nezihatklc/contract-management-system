package com.project.cms.service;

import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;


 /* User-related operations are defined*/
 /* Login, password change and manager-level user management. */

public interface UserService {

    // User login with username + password 
    User login(String username, String password)
            throws InvalidCredentialsException, UserNotFoundException;

    // Logged-in user can change own password 
    void changePassword(int userId, String oldPass, String newPass)
            throws ValidationException, InvalidCredentialsException;

    // Returns permissions based on the user's role 
    RolePermissions getPermissionsFor(User user);

    // Manager-only: Create new user 
    User createUser(User user)
            throws ValidationException;

    // Manager-only: Update existing user 
    void updateUser(User user)
            throws ValidationException, UserNotFoundException;

    // Manager-only: Delete user 
    void deleteUser(int userId)
            throws UserNotFoundException;
}


package com.project.cms.service;

import com.project.cms.dao.user.UserDao;
import com.project.cms.dao.user.UserDaoImpl;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;

import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;
import com.project.cms.util.PasswordHasher;
import com.project.cms.util.Validator;


 // Implementation of user-related operations such as login, password update and manager-level user management.
 
public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    /* LOGIN */

    @Override
    public User login(String username, String password)
            throws InvalidCredentialsException, UserNotFoundException {

        // Find user by username
        User user = userDao.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException("User not found.");

        // Check password
        if (!PasswordHasher.verifyPassword(password, user.getPasswordHash()))
            throw new InvalidCredentialsException("Wrong password!");

        return user;
    }

    /*  CHANGE PASSWORD */

    @Override
    public void changePassword(int userId, String oldPass, String newPass)
            throws ValidationException, InvalidCredentialsException {

        // Check if user exists
        User user = userDao.findById(userId);
        if (user == null)
            throw new ValidationException("User not found.");

        // Old password must match
        if (!PasswordHasher.verifyPassword(oldPass, user.getPasswordHash()))
            throw new InvalidCredentialsException("Old password is incorrect.");

        // New password validation
        if (!Validator.isValidPassword(newPass))
            throw new ValidationException("Weak password.");

        // Save new password
        String hashed = PasswordHasher.hashPassword(newPass);
        userDao.updatePassword(userId, hashed);
    }

    /* PERMISSIONS */

    @Override
    public RolePermissions getPermissionsFor(User user) {
        if (user == null || user.getRole() == null)
            throw new IllegalArgumentException("User or role must not be null.");

        // Role creates its own permission object
        return user.getRole().createPermissions();
    }

    /* CREATE USER  */

    @Override
    public User createUser(User newUser)
            throws ValidationException {

        // Check role permissions (manager only)
        try {
            getPermissionsFor(newUser).addNewUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can create users.");
        }

        // Validate username
        if (!Validator.isValidUsername(newUser.getUsername()))
            throw new ValidationException("Invalid username.");

        // Hash password before saving
        newUser.setPasswordHash(PasswordHasher.hashPassword(newUser.getPasswordHash()));

        return userDao.save(newUser);
    }

    /* UPDATE USER */

    @Override
    public void updateUser(User user)
            throws ValidationException, UserNotFoundException {

        // Ensure user exists
        User existing = userDao.findById(user.getId());
        if (existing == null)
            throw new UserNotFoundException("User not found.");

        // Check if manager has permission
        try {
            getPermissionsFor(user).updateExistingUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can update users.");
        }

        // Validate username
        if (!Validator.isValidUsername(user.getUsername()))
            throw new ValidationException("Invalid username.");

        userDao.update(user);
    }

    /* DELETE USER */

    @Override
    public void deleteUser(int userId)
            throws UserNotFoundException {

        // Find user to delete
        User target = userDao.findById(userId);
        if (target == null)
            throw new UserNotFoundException("User not found.");

        // Manager check
        try {
            getPermissionsFor(target).deleteExistingUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can delete users.");
        }

        userDao.delete(userId);
    }
}

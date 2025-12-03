package com.project.cms.service;

import com.project.cms.dao.user.UserDao;
import com.project.cms.dao.user.UserDaoImpl;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;

import com.project.cms.model.UndoAction;
import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;
import com.project.cms.util.PasswordHasher;
import com.project.cms.util.Validator;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();
    private final UndoService undoService = new UndoServiceImpl();

    /* ===================== LOGIN ===================== */

    @Override
    public User login(String username, String password)
            throws InvalidCredentialsException, UserNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException("User not found.");

        if (!PasswordHasher.verifyPassword(password, user.getPasswordHash()))
            throw new InvalidCredentialsException("Invalid password.");

        return user;
    }

    /* ===================== CHANGE PASSWORD ===================== */

    @Override
    public void changePassword(int userId, String oldPass, String newPass)
            throws ValidationException, InvalidCredentialsException {

        User user = userDao.getUserById(userId);
        if (user == null)
            throw new ValidationException("User not found.");

        // Password can only be changed by the user himself
        if (!PasswordHasher.verifyPassword(oldPass, user.getPasswordHash()))
            throw new InvalidCredentialsException("Old password is incorrect.");

        if (!Validator.isValidPassword(newPass))
            throw new ValidationException("Weak password.");

        String hashed = PasswordHasher.hashPassword(newPass);
        userDao.updatePassword(userId, hashed);
    }

    /* ===================== ROLE PERMISSIONS ===================== */

    @Override
    public RolePermissions getPermissionsFor(User user) {
        if (user == null || user.getRole() == null)
            throw new IllegalArgumentException("User or role cannot be null.");
        return user.getRole().createPermissions();
    }

    /* ===================== CREATE USER (Manager) ===================== */

    @Override
    public User createUser(User newUser, User performingUser)
        throws ValidationException, AccessDeniedException {

        RolePermissions perm = getPermissionsFor(performingUser);
        try {
            perm.addNewUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can create users.");
        }

        // Validate user fields
        Validator.validateUser(newUser);

        // Hash password before storing
        newUser.setPasswordHash(PasswordHasher.hashPassword(newUser.getPasswordHash()));

        int newId = userDao.addUser(newUser);
        newUser.setUserId(newId);

        // Undo: CREATE USER → undo = delete user
        undoService.recordUndoAction(performingUser,
                UndoAction.forUserCreate(newUser));

        return newUser;
    }

    /* ===================== UPDATE USER (Manager) ===================== */

    @Override
    public void updateUser(User updatedUser, User performingUser)
            throws ValidationException, UserNotFoundException, AccessDeniedException {

        RolePermissions perm = getPermissionsFor(performingUser);
        try {
            perm.updateExistingUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can update users.");
        }

        User oldUser = userDao.getUserById(updatedUser.getUserId());
        if (oldUser == null)
            throw new UserNotFoundException("User not found.");

        // Manager MUST NOT change password here
        updatedUser.setPasswordHash(oldUser.getPasswordHash());

        Validator.validateUser(updatedUser);

        userDao.updateUser(updatedUser);

        // Undo: UPDATE USER → undo = restore old user
        undoService.recordUndoAction(performingUser,
                UndoAction.forUserUpdate(oldUser, updatedUser));
    }

    /* ===================== DELETE USER (Manager) ===================== */

    @Override
    public void deleteUser(int targetUserId, User performingUser)
            throws UserNotFoundException, AccessDeniedException {

        RolePermissions perm = getPermissionsFor(performingUser);
        try {
            perm.deleteExistingUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can delete users.");
        }

        // Manager cannot delete himself
        if (performingUser.getUserId() == targetUserId)
            throw new AccessDeniedException("You cannot delete your own account.");

        User oldUser = userDao.getUserById(targetUserId);
        if (oldUser == null)
            throw new UserNotFoundException("User not found.");

        userDao.deleteUser(targetUserId);

        // Undo: DELETE USER → undo = recreate user
        undoService.recordUndoAction(performingUser,
                UndoAction.forUserDelete(oldUser));
    }
}

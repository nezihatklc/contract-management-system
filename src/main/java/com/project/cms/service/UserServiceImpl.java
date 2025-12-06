package com.project.cms.service;

import com.project.cms.dao.user.UserDao;
import com.project.cms.dao.user.UserDaoImpl;
import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;
import com.project.cms.model.RoleType;
import com.project.cms.model.role.RolePermissions;
import com.project.cms.util.PasswordHasher;
import com.project.cms.util.Validator;
import java.util.List;

/**
 * Implementation of UserService.
 * Handles login, password changes, user management, and undo support.
 *
 * @author Simay
 */

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    private  UndoService undoService;

    /**
     * Creates a new UserServiceImpl instance.
     * @param undoService - the undo service used for recording undo actions
     */
    public UserServiceImpl(UndoService undoService) {
        this.undoService = undoService;
    }

    /**
     * For replacing the undo service.
     * @param undoService - the undo service to use
     */
    public void setUndoService(UndoService undoService) {
        this.undoService = undoService;
    }    

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

        // Capture old state for undo
        User oldUserCopy = new User(user);

        String hashed = PasswordHasher.hashPassword(newPass);
        userDao.updatePassword(userId, hashed);

        // Capture new state for undo
        User newUserCopy = new User(user);
        newUserCopy.setPasswordHash(hashed);

        undoService.recordUndoAction(user, UndoAction.forPasswordChange(oldUserCopy, newUserCopy));
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
        return createUser(newUser, performingUser, true);
    }

    @Override
    public User createUser(User newUser, User performingUser, boolean recordUndo)
            throws ValidationException, AccessDeniedException {

        // Permission check
        RolePermissions perm = getPermissionsFor(performingUser);
        try {
            perm.addNewUser();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can create users.");
        }

        // Validate non-password fields (name, surname, phone, etc.)
        Validator.validateUser(newUser);

        // Validate plaintext password
        if (newUser.getPlainPassword() == null || newUser.getPlainPassword().isEmpty()) {
            throw new ValidationException("Password cannot be empty.");
        }

        if (!Validator.isValidPassword(newUser.getPlainPassword())) {
             throw new ValidationException("Weak password. Must be at least 8 chars, contain a digit and an uppercase letter.");
        }

        // Hash the plaintext password
        String hashed = PasswordHasher.hashPassword(newUser.getPlainPassword());
        newUser.setPasswordHash(hashed);

        // Clear plaintext password from memory (security best practice)
        newUser.setPlainPassword(null);

        // Save to DB
        int newId = userDao.addUser(newUser);
        newUser.setUserId(newId);

        // Undo support
        if (recordUndo) {
            undoService.recordUndoAction(
                    performingUser,
                    UndoAction.forUserCreate(newUser)
            );
        }

        return newUser;
    }


    /* ===================== UPDATE USER (Manager) ===================== */

    @Override
    public void updateUser(User updatedUser, User performingUser)
            throws ValidationException, UserNotFoundException, AccessDeniedException {
        updateUser(updatedUser, performingUser, true);
    }

    @Override
    public void updateUser(User updatedUser, User performingUser, boolean recordUndo)
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
        if (recordUndo) {
            undoService.recordUndoAction(performingUser,
                    UndoAction.forUserUpdate(oldUser, updatedUser));
        }
    }

    /* ===================== DELETE USER (Manager) ===================== */

    @Override
    public void deleteUser(int targetUserId, User performingUser)
            throws UserNotFoundException, AccessDeniedException {
        deleteUser(targetUserId, performingUser, true);
    }

    @Override
    public void deleteUser(int targetUserId, User performingUser, boolean recordUndo)
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

        
        User oldUserDb = userDao.getUserById(targetUserId);
        if (oldUserDb == null)
            throw new UserNotFoundException("User not found.");

        // 2) Create copy of old User for undo
        User oldUserCopy = new User(oldUserDb);

        // 3) delete user
        userDao.deleteUser(targetUserId);

    
        if (recordUndo) {
            undoService.recordUndoAction(
                    performingUser,
                    UndoAction.forUserDelete(oldUserCopy)
            );
        }
    }


    @Override
        public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    


    @Override
    public void restorePreviousPassword(int userId, String oldPasswordHash, User performingUser)
            throws UserNotFoundException, AccessDeniedException {

        User user = userDao.getUserById(userId);
        if (user == null)
            throw new UserNotFoundException("User not found.");

        boolean isSelf = (performingUser.getUserId() == userId);
        boolean isManager = (performingUser.getRole() == RoleType.MANAGER);

        if (!isSelf && !isManager) {
            throw new AccessDeniedException("Cannot restore password for another user.");
        }

        userDao.updatePassword(userId, oldPasswordHash);
    }
}

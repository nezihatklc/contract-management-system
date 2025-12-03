package com.project.cms.service;

import com.project.cms.dao.user.UserDao;
import com.project.cms.dao.user.UserDaoImpl;
import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;
import com.project.cms.util.PasswordHasher;
import com.project.cms.util.Validator;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    /*  LOGIN */
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

    /*  CHANGE PASSWORD */
    @Override
    public void changePassword(int userId, String oldPass, String newPass)
            throws ValidationException, InvalidCredentialsException {

        User user = userDao.getUserById(userId);
        if (user == null)
            throw new ValidationException("User not found.");

        if (!PasswordHasher.verifyPassword(oldPass, user.getPasswordHash()))
            throw new InvalidCredentialsException("Old password is incorrect.");

        if (!Validator.isValidPassword(newPass))
            throw new ValidationException("Weak password.");

        String hashed = PasswordHasher.hashPassword(newPass);
        userDao.updatePassword(userId, hashed);
    }

    /* GET ROLE PERMISSIONS */
    @Override
    public RolePermissions getPermissionsFor(User user) {
        if (user == null || user.getRole() == null)
            throw new IllegalArgumentException("User or role cannot be null.");
        return user.getRole().createPermissions();
    }

    /*  MANAGER: CREATE USER  */
    @Override
    public User createUser(User user)
            throws ValidationException {

        // Validate fields (username, name, etc.)
        Validator.validateUser(user);

        // Hash password before saving
        user.setPasswordHash(PasswordHasher.hashPassword(user.getPasswordHash()));
        
        int id = userDao.addUser(user);
        user.setUserId(id);
        return user;
     }

    /* MANAGER: UPDATE USER */
    @Override
    public void updateUser(User user)
            throws ValidationException, UserNotFoundException {

        User existing = userDao.getUserById(user.getUserId());
        if (existing == null)
            throw new UserNotFoundException("User not found.");

        Validator.validateUser(user);

        userDao.updateUser(user);
    }

    /* MANAGER: DELETE USER  */
    @Override
    public void deleteUser(int userId)
            throws UserNotFoundException {

        User existing = userDao.getUserById(userId);
        if (existing == null)
            throw new UserNotFoundException("User not found.");

        userDao.deleteUser(userId);
    }
}

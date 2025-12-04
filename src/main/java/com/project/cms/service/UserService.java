package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.InvalidCredentialsException;
import com.project.cms.exception.AppExceptions.UserNotFoundException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;
import java.util.List;

public interface UserService {

        // LOGIN
        User login(String username, String password)
                throws InvalidCredentialsException, UserNotFoundException;

        // CHANGE PASSWORD
        void changePassword(int userId, String oldPass, String newPass)
                throws ValidationException, InvalidCredentialsException;

        // GET ROLE PERMISSIONS
        RolePermissions getPermissionsFor(User user);

        // MANAGER: CREATE USER
        User createUser(User newUser, User performingUser)
                throws ValidationException, AccessDeniedException;

        User createUser(User newUser, User performingUser, boolean recordUndo)
                throws ValidationException, AccessDeniedException;

        // MANAGER: UPDATE USER
        void updateUser(User updatedUser, User performingUser)
                throws ValidationException, UserNotFoundException, AccessDeniedException;

        void updateUser(User updatedUser, User performingUser, boolean recordUndo)
                throws ValidationException, UserNotFoundException, AccessDeniedException;

        // MANAGER: DELETE USER
        void deleteUser(int targetUserId, User performingUser)
                throws UserNotFoundException, AccessDeniedException;

        void deleteUser(int targetUserId, User performingUser, boolean recordUndo)
                throws UserNotFoundException, AccessDeniedException;

        List<User> getAllUsers();

}

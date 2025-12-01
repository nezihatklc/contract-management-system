package com.project.cms.service;

import com.project.cms.model.User;
import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;
import com.project.cms.exception.InvalidCredentialsException;
import com.project.cms.exception.UserNotFoundException;
import com.project.cms.exception.ValidationException;

public interface UserService {

    /* Called when the user tries to log in. */
    User login(String userName, String passwordPlain)
    throws InvalidCredentialsException, UserNotFoundException;

    /* The user logged into the system updates own password. */
    void changePassword(int userId, String oldPlain, String newPlain)
    throws ValidationException, InvalidCredentialsException;

    /* For Manager: Adding a new user */
    User createUser(User user) throws ValidationException;

    /* For Manager: User update */
    void updateUser(User user) throws ValidationException, UserNotFoundException;

    /*For Manager: Delete User */
    void deletUser(int userId) throws UserNotFoundException;
    
}

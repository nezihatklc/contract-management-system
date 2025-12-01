package com.project.cms.service;

import com.project.cms.dao.user.UserDao;
import com.project.cms.dao.user.UserDaoImpl;
import com.project.cms.exception.InvalidCredentialsException;
import com.project.cms.exception.UserNotFoundException;
import com.project.cms.exception.ValidationException;
import com.project.cms.model.Role;
import com.project.cms.model.User;
import com.project.cms.util.PasswordHasher;
import com.project.cms.util.Validator;


public class UserServiceImpl implements UserService  {
    
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String userName, String passwordPlain)
    throws InvalidCredentialsException, UserNotFoundException{

        User user = userDao.findByUsername(userName);
        if(user==null){
            throw new UserNotFoundException("User not found.");
        }

        boolean match = PasswordHasher.verifyPassword(passwordPlain, user.getPasswordHash());
        if(!match){
            throw new InvalidCredentialsException("Password is wrong!");
        }

        return user;
    }

    @Override
    public void changePassword(int userId, String oldPlain, String newPlain)
    throws ValidationException, InvalidCredentialsException{

        User user = userDao.findById(userId);
        if(user==null){
            throw new ValidationException("User not found.");
        }

        /* Is the old password correct? */
        boolean match = PasswordHasher.verifyPassword(oldPlain, user.getPasswordHash());
        if(!match){
            throw new InvalidCredentialsException("The old password is wrong!");
        }

        /* Is the new password valid? */
        if(!Validator.isValidPassword(newPlain)){
            throw new ValidationException("The new password does not meet the criteria.");
        }

        String hashed = PasswordHasher.hashPassword(newPlain);
        userDao.updatePassword(userId, hashed);
    }

    @Override
    public User createUser(User user) throws ValidationException{

        /* Only Manager can create */
        if(user.getRole()==null){
            throw new ValidationException("The User role must be specified.");
        }

        /* Validation */
        if(!Validator.isValidUsername(user.getUsername())){
            throw new ValidationException("Username is invalid.");
        }

        /* Password hash */
        String hashed = PasswordHasher.hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashed);

        return userDao.save(user);
    }

    @Override
    public void updateUser(User user)
    throws ValidationException, UserNotFoundException{

        User existing = userDao.findById(user.getId());
        if(existing==null){
            throw new UserNotFoundException("User not found.");
        }

        if(!Validator.isValidUsername(user.getUsername())){
            throw new ValidationException("Username is invalid.");
        }
        userDao.update(user);
    }

    @Override
    public void deleteUser(int userId)
    throws UserNotFoundException{

        User existing = userDao.findById(userId);
        if(existing==null){
            throw new UserNotFoundException("User not found.");
        }
        userDao.delete(userId);
    }


}

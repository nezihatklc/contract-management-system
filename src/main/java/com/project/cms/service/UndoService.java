package com.project.cms.service;

import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.UndoOperationException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;
/**
 * Handles undo actions for users.
 * Stores undo history and performs undo operations.
 *
 * @author Simay
 */


public interface UndoService {

    /**
     * Records a new undo action for a user.
     * @param user - the user who performed the action
     * @param action - the undo action to store
     */
    void recordUndoAction(User user, UndoAction action);

    
    /**
     * Undoes the most recent action of the given user.
     * @param performingUser - the user requesting the undo
     * @throws UndoOperationException - if there is no action to undo
     * @throws ValidationException - if the undo causes invalid data
     * @throws ContactNotFoundException - if a required contact is missing
     * @throws AccessDeniedException - if the user is not allowed to undo
     */
    void undo(User performingUser)
            throws UndoOperationException,
                   ValidationException,
                   ContactNotFoundException,
                   AccessDeniedException;
}

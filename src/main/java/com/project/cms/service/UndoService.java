package com.project.cms.service;

import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.UndoOperationException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;

/* Handles undo history and executing undo operations for each user. */
public interface UndoService {

    /* Store a new undo action for a user */
    void recordUndoAction(User user, UndoAction action);

    /* Undo the most recent action performed by this user */
    void undo(User performingUser)
            throws UndoOperationException,
                   ValidationException,
                   ContactNotFoundException,
                   AccessDeniedException;
}

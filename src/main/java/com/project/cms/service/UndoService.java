package com.project.cms.service;

import com.project.cms.exception.AppExceptions.UndoOperationException;
import com.project.cms.model.User;
import com.project.cms.model.undo.UndoAction;


/* Manages undo operations for contacts. */
/* Stores actions and restores previous states when needed. */

public interface UndoService {

    /* Save an undo action for this user */
    void recordUndoAction(User user, UndoAction action);

    /* Undo the last action performed by this user */
    void undo(User performingUser) throws UndoOperationException;
}

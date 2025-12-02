package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.UndoOperationException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;

/* Manages undo operations for contacts. */
public interface UndoService {

    void recordUndoAction(User user, UndoAction action);

    void undo(User performingUser) throws UndoOperationException , AccessDeniedException, ValidationException, ContactNotFoundException;

    
}


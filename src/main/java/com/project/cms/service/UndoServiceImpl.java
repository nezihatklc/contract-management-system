package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.UndoOperationException;

import com.project.cms.model.User;
import com.project.cms.model.undo.UndoAction;
import com.project.cms.model.undo.UndoAction.ActionType;
import com.project.cms.model.Contact;
import com.project.cms.model.role.RolePermissions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
/* Handles undo logic using a stack. */
/* Supports multiple undo operations in order. */
/* Checks user role permissions before undoing. */

public class UndoServiceImpl implements UndoService {

    // Each user has their own undo history
    private final Map<Integer, Stack<UndoAction>> undoStacks = new HashMap<>();

    private final UserService userService = new UserServiceImpl();
    private final ContactService contactService = new ContactServiceImpl();

    /* RECORD ACTION */

    @Override
    public void recordUndoAction(User user, UndoAction action) {
        undoStacks.computeIfAbsent(user.getId(), id -> new Stack<>()).push(action);
    }

    /* UNDO OPERATION */

    @Override
    public void undo(User performingUser) throws UndoOperationException {

        // Check role permissions via RolePermissions
        RolePermissions perm = userService.getPermissionsFor(performingUser);

        boolean canUndoUpdate = canUndoUpdate(perm);
        boolean canUndoCreateDelete = canUndoCreateDelete(perm);

        if (!canUndoUpdate && !canUndoCreateDelete)
            throw new AccessDeniedException("You don't have permission to undo.");

        Stack<UndoAction> stack = undoStacks.get(performingUser.getId());
        if (stack == null || stack.isEmpty())
            throw new UndoOperationException("No actions to undo.");

        UndoAction action = stack.pop();

        switch (action.getType()) {

            case CREATE -> {
                if (!canUndoCreateDelete)
                    throw new AccessDeniedException("Only Senior Developer can undo creation.");
                contactService.deleteContact(action.getNewState().getId(), performingUser);
            }

            case UPDATE -> {
                if (!canUndoUpdate)
                    throw new AccessDeniedException("Only Junior/Senior can undo updates.");
                contactService.updateContact(action.getPreviousState(), performingUser);
            }

            case DELETE -> {
                if (!canUndoCreateDelete)
                    throw new AccessDeniedException("Only Senior Developer can undo deletions.");
                contactService.createContact(action.getPreviousState(), performingUser);
            }
        }
    }

    /*  HELPERS */

    // Checks if this role is allowed to undo updates (Junior + Senior)
    private boolean canUndoUpdate(RolePermissions perm) {
        try {
            perm.updateExistingContact(); 
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }


    // Check if this role is allowed to undo create/delete actions (Only Senior)
    private boolean canUndoCreateDelete(RolePermissions perm) {
        try {
            perm.addNewContactOrContacts(); 
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }
}

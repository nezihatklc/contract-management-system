package com.project.cms.service;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ContactNotFoundException;
import com.project.cms.exception.AppExceptions.UndoOperationException;
import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.Contact;
import com.project.cms.model.UndoAction;
import com.project.cms.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class UndoServiceImpl implements UndoService {

    private final Map<Integer, Stack<UndoAction>> undoStacks = new HashMap<>();

    private final ContactService contactService;
    private final UserService userService;

    public UndoServiceImpl(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    /* ============================================
       RECORD UNDO ACTION
       ============================================ */
    @Override
    public void recordUndoAction(User user, UndoAction action) {
        undoStacks.computeIfAbsent(user.getUserId(), id -> new Stack<>()).push(action);
    }

    /* ============================================
       UNDO OPERATION
       ============================================ */
    @Override
    public void undo(User performingUser)
            throws UndoOperationException, ValidationException,
                   ContactNotFoundException, AccessDeniedException {

        Stack<UndoAction> stack = undoStacks.get(performingUser.getUserId());

        if (stack == null || stack.isEmpty())
            throw new UndoOperationException("No actions to undo.");

        UndoAction action = stack.pop();

        switch (action.getType()) {

            /* ==================================================
               CONTACT UNDO OPERATIONS
               ================================================== */
            case CONTACT_CREATE -> {
                Contact created = action.getNewContact();
                contactService.deleteContact(created.getContactId(), performingUser);
            }

            case CONTACT_UPDATE -> {
                contactService.updateContact(action.getOldContact(), performingUser);
            }

            case CONTACT_DELETE -> {
                contactService.restoreContact(action.getOldContact(), performingUser);
            }

            /* ===========================
               USER UNDO OPERATIONS
               =========================== */
            case USER_CREATE -> {
                try {
                    User created = action.getNewUser();
                    if (created == null)
                        throw new UndoOperationException("Invalid undo data.");
                    userService.deleteUser(created.getUserId(), performingUser, false);
                } catch (Exception e) {
                    throw new UndoOperationException("Cannot undo user creation: " + e.getMessage());
                }
            }

            case USER_UPDATE -> {
                try {
                    User oldUser = action.getOldUser();
                    if (oldUser == null)
                        throw new UndoOperationException("Invalid undo data.");
                    userService.updateUser(oldUser, performingUser, false);
                } catch (Exception e) {
                    throw new UndoOperationException("Cannot undo user update: " + e.getMessage());
                }
            }

            case USER_DELETE -> {
                try {
                    User deletedUser = action.getOldUser();
                    if (deletedUser == null)
                        throw new UndoOperationException("Invalid undo data.");
                    userService.createUser(deletedUser, performingUser, false);
                } catch (Exception e) {
                    throw new UndoOperationException("Cannot undo user deletion: " + e.getMessage());
                }
            }
        } 
    } 
} 

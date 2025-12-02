package com.project.cms.model.undo;

import com.project.cms.model.Contact;


/* Stores the details of an action so it can be undone later. */
/* Keeps action type and previous/new contact states. */
 
public class UndoAction {

    public enum ActionType {
        CREATE,
        UPDATE,
        DELETE
    }

    private final ActionType type;
    private final Contact previousState;
    private final Contact newState;

    public UndoAction(ActionType type, Contact previousState, Contact newState) {
        this.type = type;
        this.previousState = previousState;
        this.newState = newState;
    }

    public ActionType getType() {
        return type;
    }

    public Contact getPreviousState() {
        return previousState;
    }

    public Contact getNewState() {
        return newState;
    }
}

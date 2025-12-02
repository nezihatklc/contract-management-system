package com.project.cms.service;

import java.util.Stack;

public class UndoService {
    
    private Stack<Command> commandStack = new Stack<>();

    public void addCommand(Command command) {
        commandStack.push(command);
    }

    public void undoLast() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
            System.out.println("Last operation undone.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }
    
    public boolean canUndo() {
        return !commandStack.isEmpty();
    }
}

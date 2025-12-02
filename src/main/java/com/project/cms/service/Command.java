package com.project.cms.service;

public interface Command {
    void execute();
    void undo();
}

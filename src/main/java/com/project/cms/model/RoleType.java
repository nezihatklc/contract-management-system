package com.project.cms.model;

import com.project.cms.model.role.*;

public enum RoleType {
    TESTER,
    JUNIOR_DEVELOPER,
    SENIOR_DEVELOPER,
    MANAGER;

    // Factory method to create RolePermissions based on Role type
    public RolePermissions createPermissions() {
        return switch (this) {
            case TESTER -> new TesterRole();
            case JUNIOR_DEVELOPER -> new JuniorDeveloperRole();
            case SENIOR_DEVELOPER -> new SeniorDeveloperRole();
            case MANAGER -> new ManagerRole();
        };
    }
}

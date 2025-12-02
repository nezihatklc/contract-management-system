package com.project.cms.model;

import com.project.cms.model.role.*;

public enum RoleType {

    TESTER {
        @Override
        public RolePermissions createPermissions() {
            return new TesterRole();
        }
    },
    JUNIOR_DEVELOPER {
        @Override
        public RolePermissions createPermissions() {
            return new JuniorDeveloperRole();
        }
    },
    SENIOR_DEVELOPER {
        @Override
        public RolePermissions createPermissions() {
            return new SeniorDeveloperRole();
        }
    },
    MANAGER {
        @Override
        public RolePermissions createPermissions() {
            return new ManagerRole();
        }
    };

    public abstract RolePermissions createPermissions();
}


package com.project.cms.model;

import com.project.cms.model.role.*;
/**
 * Enumeration representing the specific user roles within the Contact Management System.
 * <p>
 * This enum functions as a <b>Factory</b> for role permissions. Each enum constant
 * implements the abstract {@link #createPermissions()} method to return the specific
 * {@link RolePermissions} strategy associated with that role.
 * <p>
 * This design leverages <b>Polymorphism</b> to decouple the role definition from
 * its specific behavior/permissions.
 */
public enum RoleType {
/**
     * Tester role: Read-only access to contacts.
     */
    TESTER {
        @Override
        public RolePermissions createPermissions() {
            return new TesterRole();
        }
    },
    /**
     * Junior Developer role: Can read and update contacts, but cannot create or delete.
     */
    JUNIOR_DEVELOPER {
        @Override
        public RolePermissions createPermissions() {
            return new JuniorDeveloperRole();
        }
    },
    /**
     * Senior Developer role: Full CRUD access to contacts (Create, Read, Update, Delete).
     */
    SENIOR_DEVELOPER {
        @Override
        public RolePermissions createPermissions() {
            return new SeniorDeveloperRole();
        }
    },
    /**
     * Manager role: Administrative access (User management, Statistics), but no direct contact modification.
     */
    MANAGER {
        @Override
        public RolePermissions createPermissions() {
            return new ManagerRole();
        }
    };
    /**
     * Abstract factory method that must be implemented by each role constant.
     * <p>
     * This method instantiates and returns the specific permission strategy class
     * (e.g., {@code TesterRole}, {@code ManagerRole}) corresponding to the enum value.
     *
     * @return The concrete {@link RolePermissions} implementation for this role.
     */

    public abstract RolePermissions createPermissions();
}


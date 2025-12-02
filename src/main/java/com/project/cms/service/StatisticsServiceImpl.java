package com.project.cms.service;

import com.project.cms.dao.statistics.StatisticsDao;
import com.project.cms.dao.statistics.StatisticsDaoImpl;

import com.project.cms.exception.AppExceptions.AccessDeniedException;
import com.project.cms.exception.AppExceptions.ValidationException;

import com.project.cms.model.User;
import com.project.cms.model.role.RolePermissions;


/* Implements statistical operations. */
/* Only Manager is allowed to view statistics. */
 
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDao statsDao = new StatisticsDaoImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public int getTotalContactCount(User performingUser) throws ValidationException {

        // Role permission check
        RolePermissions perm = userService.getPermissionsFor(performingUser);

        try {
            // Only Manager has this permission
            perm.showContactsStatisticalInfo();
        } catch (UnsupportedOperationException e) {
            throw new AccessDeniedException("Only Manager can view statistics.");
        }

        // If allowed- return statistics
        return statsDao.totalContacts();
    }
}

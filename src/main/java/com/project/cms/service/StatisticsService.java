package com.project.cms.service;

import com.project.cms.exception.AppExceptions.ValidationException;
import com.project.cms.model.User;


 /* Provides access to contact statistics. */
 /* Only the Manager role can use these operations. */

public interface StatisticsService {

    /* Manager-only: get total number of contacts in the system */
    int getTotalContactCount(User performingUser) throws ValidationException;

}

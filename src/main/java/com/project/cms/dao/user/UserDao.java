package com.project.cms.dao.user;

import com.project.cms.model.User;

public interface UserDao {
    User findByUsername(String username);
}

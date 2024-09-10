package org.somemate.demo.user.service;

import org.somemate.demo.user.dto.User;

import java.sql.SQLException;

public interface UserService {
    User getTestUser() throws SQLException;
}

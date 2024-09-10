package org.somemate.demo.user.service;

import org.somemate.demo.user.dao.UserDao;
import org.somemate.demo.user.dto.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImp implements UserService {
    UserDao userDao;
    public UserServiceImp(UserDao userDao) {this.userDao = userDao;}

    @Override
    public User getTestUser() throws SQLException {
        return userDao.getTestUser();
    }
}

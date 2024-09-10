package org.somemate.demo.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;

@Mapper
public interface UserDao {
    User getTestUser() throws SQLException;
}

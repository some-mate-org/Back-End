package org.somemate.demo.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserDao {

    void saveUser(User user) throws SQLException;

    // ID로 사용자를 찾는 메서드
    User findById(@Param("id") int id) throws SQLException;

    String getUserMBTI(int userIdx) throws SQLException;

    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    int existsByUserID(String userID)throws SQLException;

    boolean existsByUserId(String userId);
}

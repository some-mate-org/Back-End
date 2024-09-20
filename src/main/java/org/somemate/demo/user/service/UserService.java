package org.somemate.demo.user.service;

import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {

    // 사용자 ID로 사용자 정보 조회
    User getUserById(int id) throws SQLException;

    String getUserMBTI(int userIdx) throws SQLException;

    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    void registerUser(User user)throws SQLException;

    boolean isUserIdAvailable(String userId)throws SQLException;
}

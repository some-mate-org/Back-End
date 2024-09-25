package org.somemate.demo.user.service;

import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {

    // 사용자 ID로 사용자 정보 조회
    User getUserById(int id) throws SQLException;

    Map<String,Object> getUserMbtiAndIdx(String userId) throws SQLException;

    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    void registerUser(User user)throws SQLException;

    boolean isUserIdAvailable(String userId)throws SQLException;

    User authenticateUser(String userId, String password) throws SQLException;

    void updateRefreshToken(String userId, String refreshToken) throws SQLException;

    String getRefreshTokenByUserId(String userId) throws SQLException;

    User getUserByUserId(String userId) throws SQLException;

    int getUserIdx(String userId)throws SQLException;
}

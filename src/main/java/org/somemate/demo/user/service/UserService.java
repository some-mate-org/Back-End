package org.somemate.demo.user.service;

import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {
    User getTestUser() throws SQLException;

    // 사용자 등록 메서드
    void registerUser(User user) throws SQLException, IllegalArgumentException;

    // 사용자 로그인 메서드, 로그인 성공 시 토큰 반환
    String loginUser(String user_ID, String password) throws SQLException;

    // 사용자 로그아웃
    void logoutUser(String refreshToken) throws SQLException;

    // 사용자 ID로 사용자 정보 조회
    User getUserById(int id) throws SQLException;

    boolean checkUserID(String user_ID) throws SQLException;

    String getUserMBTI(int userIdx) throws SQLException;

    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    int updateUserMbti(User user) throws SQLException;
}

package org.somemate.demo.user.service;

import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {

    // 사용자 ID를 기반으로 MBTI와 인덱스를 조회하는 메서드
    Map<String,Object> getUserMbtiAndIdx(String userId) throws SQLException;

    // 매칭된 사용자 정보를 조회하는 메서드
    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    // 회원가입 처리 메서드 - 사용자를 등록
    void registerUser(User user) throws SQLException;

    // 사용자 ID의 사용 가능 여부를 확인하는 메서드
    boolean isUserIdAvailable(String userId) throws SQLException;

    // 사용자 인증을 처리하는 메서드 - ID와 비밀번호를 확인
    User authenticateUser(String userId, String password) throws SQLException;

    // 사용자 ID를 사용하여 refreshToken을 업데이트하는 메서드
    void updateRefreshToken(String userId, String refreshToken) throws SQLException;

    // 사용자 ID로 refreshToken을 가져오는 메서드
    String getRefreshTokenByUserId(String userId) throws SQLException;

    // 사용자 ID로 사용자 정보를 조회하는 메서드
    User getUserByUserId(String userId) throws SQLException;

    // 사용자 ID로 사용자의 인덱스를 조회하는 메서드
    int getUserIdx(String userId) throws SQLException;
}

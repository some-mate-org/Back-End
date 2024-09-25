package org.somemate.demo.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserDao {

    // 사용자 정보를 데이터베이스에 저장하는 메서드
    void saveUser(User user) throws SQLException;

    // 사용자 ID를 사용하여 MBTI와 인덱스를 조회하는 메서드
    Map<String, Object> getUserMbtiAndIdx(String userId) throws SQLException;

    // 매칭된 사용자의 정보를 조회하는 메서드
    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    // 사용자 ID의 중복확인 확인하는 메서드
    boolean existsByUserId(String userId);

    // 사용자 ID를 사용하여 로그인 시 사용자를 조회하는 메서드
    User findByUserId(@Param("userId") String userId) throws SQLException;

    // 사용자 ID와 refreshToken을 사용하여 refreshToken을 업데이트하는 메서드
    void updateRefreshToken(@Param("userId") String userId, @Param("refreshToken") String refreshToken) throws SQLException;

    // 사용자 ID로 refreshToken을 조회하는 메서드
    String getRefreshTokenByUserId(@Param("userId") String userId) throws SQLException;

    // 사용자 ID로 사용자를 조회하는 메서드 (회원 조회용)
    User findUserByUserId(@Param("userId") String userId) throws SQLException;

    // 사용자 ID로 사용자의 인덱스를 조회하는 메서드
    int getUserIdx(String userId) throws SQLException;
}

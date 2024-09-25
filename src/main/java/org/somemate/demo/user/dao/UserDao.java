package org.somemate.demo.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserDao {
    User getTestUser() throws SQLException;

    // 사용자를 저장하는 메서드
    void saveUser(User user) throws SQLException;


    // 아이디 중복 검사 메서드
    boolean existsByUserID(String userID) throws SQLException;

    // 사용자 이름과 비밀번호로 사용자를 찾는 메서드
    User findByNameAndPassword(@Param("userID") String userID, @Param("password") String password) throws SQLException;

    // ID로 사용자를 찾는 메서드
    User findById(@Param("id") int id) throws SQLException;

    Map<String,Object> getUserMbtiAndIdx(String userId) throws SQLException;

    RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException;

    boolean existsByUserId(String userId);

    // userId로 사용자를 조회하는 메서드 (로그인 시 사용)
    User findByUserId(@Param("userId") String userId) throws SQLException;

    void updateRefreshToken(@Param("userId") String userId, @Param("refreshToken") String refreshToken) throws SQLException;

    String getRefreshTokenByUserId(@Param("userId") String userId) throws SQLException;

    User findUserByUserId(@Param("userId") String userId) throws SQLException;

    int getUserIdx(String userId) throws SQLException;
}

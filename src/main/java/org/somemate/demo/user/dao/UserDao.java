package org.somemate.demo.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;

@Mapper
public interface UserDao {
    User getTestUser() throws SQLException;

    // 사용자를 저장하는 메서드
    void saveUser(@Param("user") User user) throws SQLException;

    // 아이디 중복 검사 메서드
    boolean existsByUserID(String userID) throws SQLException;

    // 사용자 이름과 비밀번호로 사용자를 찾는 메서드
    User findByNameAndPassword(@Param("userID") String userID, @Param("password") String password) throws SQLException;

    // ID로 사용자를 찾는 메서드
    User findById(@Param("id") int id) throws SQLException;

    // 사용자 정보를 업데이트하는 메서드
    boolean updateUser(@Param("id") int id, @Param("user") User user) throws SQLException;

    // 사용자 RefreshToken를 삭제하는 메서드
    boolean deleteRefreshToken(String refreshToken) throws SQLException;

}

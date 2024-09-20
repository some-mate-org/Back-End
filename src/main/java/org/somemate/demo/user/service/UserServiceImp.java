package org.somemate.demo.user.service;

import org.somemate.demo.user.dao.UserDao;
import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImp implements UserService {
    private static final String SECRET_KEY = generateSecretKey();
    UserDao userDao;
    public UserServiceImp(UserDao userDao) {this.userDao = userDao;}



    @Override
    public void registerUser(User user) throws SQLException {
        userDao.saveUser(user);
    }

    // 비밀 키 생성
    private static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] values = new byte[32]; // 32 bytes = 256 bits
        random.nextBytes(values);
        return Base64.getEncoder().encodeToString(values);
    }

    // 사용자 패스워드 암호화
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 암호화에 실패했습니다.", e);
        }
    }

    // 주어진 ID로 사용자 정보를 조회
    @Override
    public User getUserById(int id) throws SQLException {
        return userDao.findById(id);
    }

    @Override
    public String getUserMBTI(int userIdx) throws SQLException {
        return userDao.getUserMBTI(userIdx);
    }

    @Override
    public RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException {
        return userDao.getMatchedUserInfo(map);
    }

    public boolean isUserIdAvailable(String userId) {
        return !userDao.existsByUserId(userId);
    }
}

package org.somemate.demo.user.service;

import org.somemate.demo.user.dao.UserDao;
import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImp implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder; // 의존성 주입
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

    // 회원가입
    @Override
    public void registerUser(User user) throws SQLException {
        // 패스워드 암호화
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);  // 암호화된 패스워드를 설정

        // DB에 사용자 정보 저장
        userDao.saveUser(user);
    }


    // 주어진 ID로 사용자 정보를 조회
    @Override
    public User getUserById(int id) throws SQLException {
        return userDao.findById(id);
    }

    @Override
    public Map<String,Object> getUserMbtiAndIdx(String userId) throws SQLException {
        return userDao.getUserMbtiAndIdx(userId);
    }

    @Override
    public RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException {
        return userDao.getMatchedUserInfo(map);
    }

    @Override
    public boolean isUserIdAvailable(String userId) {
        return !userDao.existsByUserId(userId);
    }

    @Override
    // refreshToken을 업데이트하는 메서드 추가
    public void updateRefreshToken(String userId, String refreshToken) throws SQLException {
        userDao.updateRefreshToken(userId, refreshToken);
    }

    @Override
    // DB에서 refreshToken을 가져오는 메서드
    public String getRefreshTokenByUserId(String userId) throws SQLException {
        return userDao.getRefreshTokenByUserId(userId);
    }

    @Override
    public User authenticateUser(String userId, String password) throws SQLException {
        User user = userDao.findByUserId(userId);  // DB에서 사용자 조회

        if (user != null) {
            System.out.println("find user: " + user.getUserId());
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("password same");
                return user;  // 비밀번호가 일치하면 인증 성공
            } else {
                System.out.println("password not same");
            }
        } else {
            System.out.println("not find");
        }
        return null;  // 인증 실패
    }


    @Transactional
    @Override
    public User getUserByUserId(String userId) throws SQLException {
        System.out.println("getUserByUserId called with userId: " + userId);

        // 실제 데이터베이스 조회
        User user = userDao.findUserByUserId(userId);

        if (user != null) {
            System.out.println("User found: " + user.getUserId());
        } else {
            System.out.println("User not found for userId: " + userId);
        }

        return user;
    }

    @Override
    public int getUserIdx(String userId) throws SQLException {
        return userDao.getUserIdx(userId);
    }


}

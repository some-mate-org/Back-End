package org.somemate.demo.user.service;

import org.somemate.demo.user.dao.UserDao;
import org.somemate.demo.user.dto.RecommendedUser;
import org.somemate.demo.user.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder; // 의존성 주입
    }

    // 회원가입 처리 메서드 - 사용자의 비밀번호를 암호화한 후 DB에 저장
    @Override
    public void registerUser(User user) throws SQLException {
        // 패스워드 암호화
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        // DB에 사용자 정보 저장
        userDao.saveUser(user);
    }

    // 사용자 ID로 MBTI와 인덱스를 조회하는 메서드
    @Override
    public Map<String, Object> getUserMbtiAndIdx(String userId) throws SQLException {
        return userDao.getUserMbtiAndIdx(userId);
    }

    // 매칭된 사용자의 정보를 조회하는 메서드
    @Override
    public RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException {
        return userDao.getMatchedUserInfo(map);
    }

    // 주어진 사용자 ID의 사용 가능 여부를 확인하는 메서드
    @Override
    public boolean isUserIdAvailable(String userId) {
        return !userDao.existsByUserId(userId);
    }

    // 사용자 ID를 기반으로 refreshToken을 업데이트하는 메서드
    @Override
    public void updateRefreshToken(String userId, String refreshToken) throws SQLException {
        userDao.updateRefreshToken(userId, refreshToken);
    }

    // 사용자 ID를 기반으로 refreshToken을 조회하는 메서드
    @Override
    public String getRefreshTokenByUserId(String userId) throws SQLException {
        return userDao.getRefreshTokenByUserId(userId);
    }

    // 로그인 할때 사용자의 아이디 및 패스워드 확인 메서드
    @Override
    public User authenticateUser(String userId, String password) throws SQLException {
        User user = userDao.findByUserId(userId);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("password same");
                return user;
            } else {
                System.out.println("password not same");
            }
        } else {
            System.out.println("not find");
        }
        return null;
    }

    // 사용자 ID로 사용자 정보를 조회하는 메서드 (AccessToken 발급 받을때 사용)
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

    // 사용자 ID로 사용자의 인덱스를 조회하는 메서드
    @Override
    public int getUserIdx(String userId) throws SQLException {
        return userDao.getUserIdx(userId);
    }

}

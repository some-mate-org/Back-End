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

    public boolean isUserIdAvailable(String userId) {
        return !userDao.existsByUserId(userId);
    }

    @Override
    public int getUserIdx(String userId) throws SQLException {
        return userDao.getUserIdx(userId);
    }
}

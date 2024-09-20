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
    public User getTestUser() throws SQLException {
        return userDao.getTestUser();
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

    // 사용자를 등록하는 메서드
    @Override
    public void registerUser(User user) throws SQLException, IllegalArgumentException {
        if (userDao.existsByUserID(user.getUserID())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 아이디입니다.");
        }
        user.setMbti(null); // 초기 MBTI 값은 null
        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        userDao.saveUser(user);
    }

    // 사용자 로그인과 토큰 생성
    @Override
    public String loginUser(String user_ID, String password) throws SQLException {
        User user = userDao.findByNameAndPassword(user_ID, password);
        if (user != null) {
            String encryptedPassword = encryptPassword(password);
            if (encryptedPassword.equals(user.getPassword())) {
                return generateToken(user);
            }
        }
        return null; // 로그인 실패 시 null 반환
    }

    // JWT 토큰 생성
    private String generateToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getUserID())
                .claim("name", user.getName())
                .claim("role", "user") // 사용자 권한 설정 예
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24)) // 토큰 만료 시간 설정 (24시간 후)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 사용자 ID 기준으로 로그아웃 (refreshToken 삭제)
    @Override
    public void logoutUser(String refreshToken) throws SQLException {
        // refreshToken을 데이터베이스에서 삭제하는 메서드 호출
        boolean isDeleted = userDao.deleteRefreshToken(refreshToken);
        if (!isDeleted) {
            throw new SQLException("로그아웃 실패: refreshToken 정보를 찾을 수 없습니다.");
        }
        System.out.println("로그아웃 성공: 사용자의 refreshToken이 삭제되었습니다.");
    }

    // 주어진 ID로 사용자 정보를 조회
    @Override
    public User getUserById(int id) throws SQLException {
        return userDao.findById(id);
    }

    @Override
    public boolean checkUserID(String user_ID) throws SQLException{
        return userDao.existsByUserID(user_ID);
    }

    @Override
    public String getUserMBTI(int userIdx) throws SQLException {
        return userDao.getUserMBTI(userIdx);
    }


    @Override
    public RecommendedUser getMatchedUserInfo(Map<String, Object> map) throws SQLException {
        return userDao.getMatchedUserInfo(map);
    }

    @Override
    public int updateUserMbti(User user) throws SQLException{
        return userDao.updateUserMbti(user);
    }
}

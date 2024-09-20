package org.somemate.demo.user.controller;

import org.somemate.demo.user.dto.User;
import org.somemate.demo.user.service.UserService;
import org.somemate.demo.util.JWTUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.somemate.demo.user.dto.RecommendedUser;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    UserService userService;
    JWTUtil jwtUtil;
    User user;
    RecommendedUser recommendedUser;


    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;//생성자 주입
        this.jwtUtil = jwtUtil;            //생성자 주입
    }

    @GetMapping
    @RequestMapping("/test")
    public User test() {

        try {
            user = userService.getTestUser();
            System.out.println("user service : " + user);
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws SQLException {
        userService.registerUser(user);
        return ResponseEntity.ok("유저 등록 성공");
    }

    // 사용자 로그인을 위한 엔드포인트, 성공 시 토큰 반환
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String user_ID, @RequestParam String password) throws SQLException {
        String token = userService.loginUser(user_ID, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("로그인 실패");
        }
    }

    // 사용자 로그아웃을 위한 엔드포인터, 성공 시 RefreshToken 삭제
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam("refreshToken") String refreshToken) {
        try {
            userService.logoutUser(refreshToken);  // 로그아웃 로직 호출
            return ResponseEntity.ok("성공적으로 로그아웃이 되었습니다.");
        } catch (SQLException e) {
            return ResponseEntity.badRequest().body("로그아웃 실패: " + e.getMessage());
        }
    }

    // 특정 사용자의 정보를 조회하는 엔드포인트
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws SQLException {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check-username/{id}")
    public ResponseEntity<?> checkUsernameAvailability(@PathVariable String user_ID) throws SQLException {
        boolean isAvailable = userService.checkUserID(user_ID);
        if (isAvailable) {
            return ResponseEntity.ok().body("아이디 사용 가능");
        } else {
            return ResponseEntity.badRequest().body("이미 사용 중인 아이디입니다");
        }
    }

    @GetMapping
    @RequestMapping("/getMatchedUserInfo/{userIdx}")
    public RecommendedUser getMatchedUserInfo(@PathVariable int userIdx) throws SQLException
    {
        try {
            String mbti = userService.getUserMBTI(userIdx);
            System.out.println("mbti : " + mbti);
            Map<String, Object> map = new HashMap<>();
            map.put("userIdx", userIdx);
            map.put("mbti", mbti);

            recommendedUser = userService.getMatchedUserInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recommendedUser;
    }

    @PatchMapping("/updateUserMbti")
    public int updateUserMbti(@RequestBody User user) throws SQLException {
        int result = -1;
        System.out.println("user : " + user.toString());
        try {
            result = userService.updateUserMbti(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

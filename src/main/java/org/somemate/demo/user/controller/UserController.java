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
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import java.sql.SQLException;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    RecommendedUser recommendedUser;


    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("사용자가 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 등록 중 오류가 발생했습니다: " + e.getMessage());
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

    @GetMapping("/checkUserId")
    public ResponseEntity<Boolean> checkUserId(@RequestParam String userId) {
        try {
            boolean isAvailable = userService.isUserIdAvailable(userId);
            return new ResponseEntity<>(isAvailable, HttpStatus.OK);
        } catch (Exception e) {
            // 상세한 로그 출력
            System.err.println("Error occurred while checking user ID: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 에러 발생 시 false 반환
        }
    }

        @GetMapping
        @RequestMapping("/getMatchedUserInfo/{userId}")
        public RecommendedUser getMatchedUserInfo(@PathVariable String userId) throws SQLException {
            System.out.println(userId);
            try {
                Map<String, Object> result = userService.getUserMbtiAndIdx(userId);
                System.out.println("result : " + result);
                String mbti = (String) result.get("mbti");
                int idx = (Integer) result.get("idx");

                Map<String, Object> map = new HashMap<>();
                map.put("userIdx", idx);
                map.put("mbti", mbti);

                recommendedUser = userService.getMatchedUserInfo(map);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return recommendedUser;
        }


}

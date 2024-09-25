package org.somemate.demo.user.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import java.util.logging.Logger;

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

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User loginRequest) {
        try {
            System.out.println(loginRequest);
            User user = userService.authenticateUser(loginRequest.getUserId(), loginRequest.getPassword());
            if (user != null) {
                String accessToken = jwtUtil.generateToken(user.getUserId());
                String refreshToken = jwtUtil.createRefreshToken(user.getUserId());

                // refreshToken을 DB에 저장
                userService.updateRefreshToken(user.getUserId(), refreshToken);

                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 토큰 갱신
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshAccessToken(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");

        try {
            // DB에서 저장된 refreshToken을 가져와 비교
            String storedRefreshToken = userService.getRefreshTokenByUserId(jwtUtil.getUserId(refreshToken));

            if (refreshToken.equals(storedRefreshToken) && jwtUtil.checkToken(refreshToken)) {
                String userId = jwtUtil.getUserId(refreshToken);
                String newAccessToken = jwtUtil.generateToken(userId);

                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", newAccessToken);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

    // 사용자 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        try {
            // Authorization 헤더에서 Bearer 토큰 추출
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보가 없습니다.");
            }

            // Bearer 토큰에서 실제 토큰 값만 추출
            String token = authorizationHeader.substring(7);

            // 토큰에서 userId 추출 (토큰 검증 및 파싱)
            String userId = jwtUtil.getUserId(token);

            // userId를 기준으로 DB에서 사용자 정보 조회
            User user = userService.getUserByUserId(userId);
            System.out.println(user);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보를 찾을 수 없습니다.");
            }

            // 사용자 정보 응답으로 전송
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("name", user.getName());

            userInfo.put("mbti", user.getMbti());
            userInfo.put("age", user.getAge());
            userInfo.put("profile", user.getProfile());
            userInfo.put("gender", user.getGender());
            System.out.println(userInfo.values());
            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            e.printStackTrace(); // 예외 정보 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 정보를 불러오는데 실패했습니다.");
        }

    }

    // 사용자의 ID 체크
    @GetMapping("/checkUserId")
    public ResponseEntity<Boolean> checkUserId(@RequestParam String userId) {
        try {
            boolean isAvailable = userService.isUserIdAvailable(userId);
            return new ResponseEntity<>(isAvailable, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error occurred while checking user ID: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 특정 사용자의 정보를 조회하는 엔드포인트
    @GetMapping("/getMatchedUserInfo/{userId}")
    public RecommendedUser getMatchedUserInfo(@PathVariable String userId) throws SQLException {
        try {
            Map<String, Object> result = userService.getUserMbtiAndIdx(userId);
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


    // 특정 사용자의 정보를 조회하는 엔드포인트
//    @GetMapping("/{userId}")
//    public ResponseEntity<User> getUserByUserId(@PathVariable String userId) {
//        try {
//            // 요청된 userId 로그 출력
//            System.out.println("Received request for user with ID: " + userId);
//
//            User user = userService.getUserByUserId(userId); // userId로 사용자 조회
//
//            if (user != null) {
//                // 조회된 사용자 정보 로그 출력
//                System.out.println("Found user: " + user);
//                return ResponseEntity.ok(user); // 사용자가 존재할 경우 응답
//            } else {
//                // 사용자를 찾을 수 없을 때 로그 출력
//                System.out.println("User not found with ID: " + userId);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 사용자가 없을 경우 404 반환
//            }
//        } catch (SQLException e) {
//            // 예외 발생 시 에러 로그 출력
//            System.out.println("Error retrieving user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // DB 에러 처리
//        }
//    }

}

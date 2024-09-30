package org.somemate.demo.matchingHistory.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.matchingHistory.service.HistoryService;
import org.somemate.demo.user.dto.User;
import org.somemate.demo.user.service.UserService;
import org.somemate.demo.util.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/matching")
public class HistoryController {
    private final JWTUtil jwtUtil;
    HistoryService historyService;
    UserService userService;

    public HistoryController(HistoryService historyService, JWTUtil jwtUtil, UserService userService) {
        this.historyService = historyService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(HttpServletRequest request) {
        // Authorization 헤더에서 Bearer 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보가 없습니다.");
        }

        // Bearer 토큰에서 실제 토큰 값만 추출
        String token = authorizationHeader.substring(7);

        // 토큰에서 userId 추출 (토큰 검증 및 파싱)
        String userId = jwtUtil.getUserId(token);

        try {
            int my_idx = userService.getUserIdx(userId);
            ArrayList<User> historyList = new ArrayList<>();
            historyList.addAll(historyService.getUserHistory(my_idx));

            return ResponseEntity.ok(historyList);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("매칭된 유저가 없습니다.");
        }
    }

    @PostMapping("/history/add")
    public ResponseEntity<?> postMatchingHistory(HttpServletRequest request, @RequestBody Map<String, Integer> requestBody) {
        // Authorization 헤더에서 Bearer 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보가 없습니다.");
        }

        // Bearer 토큰에서 실제 토큰 값만 추출
        String token = authorizationHeader.substring(7);

        // 토큰에서 userId 추출 (토큰 검증 및 파싱)
        String userId = jwtUtil.getUserId(token);

        int result = -1;
        try {
            int my_idx = userService.getUserIdx(userId);
            MatchingHistory matchingHistory = new MatchingHistory(my_idx, requestBody.get("recommended_idx"));
            result = historyService.postMatchingHistory(matchingHistory);
            System.out.println("insert history result : " + result);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("히스토리 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @DeleteMapping("/history/delete")
    public ResponseEntity<?> deleteMatchingHistory(
            HttpServletRequest request,
            @RequestParam("recommendedIdx") Integer recommendedIdx) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보가 없습니다.");
        }
        String token = authorizationHeader.substring(7);
        String userId = jwtUtil.getUserId(token);
        System.out.println("delete matching history : " + userId);
        try {
            int my_idx = userService.getUserIdx(userId);
            int result = historyService.deleteMatchingHistory(my_idx, recommendedIdx);

            if (result > 0) {
                return ResponseEntity.status(HttpStatus.OK).body("매칭 기록이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 매칭 기록을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("매칭 기록 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}

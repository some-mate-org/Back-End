package org.somemate.demo.matchingHistory.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.matchingHistory.service.HistoryService;
import org.somemate.demo.user.service.UserService;
import org.somemate.demo.util.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/matching")
public class HistoryController {
    HistoryService historyService;
    private final JWTUtil jwtUtil;
    UserService userService;

    public HistoryController(HistoryService historyService, JWTUtil jwtUtil, UserService userService) {
        this.historyService = historyService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/history/add")
    public ResponseEntity<?> postMatchingHistory(HttpServletRequest request, @RequestBody Map<String, Integer> requestBody ) {
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
}

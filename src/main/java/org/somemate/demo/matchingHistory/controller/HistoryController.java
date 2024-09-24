package org.somemate.demo.matchingHistory.controller;

import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.matchingHistory.service.HistoryService;
import org.somemate.demo.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/matching")
public class HistoryController {
    HistoryService historyService;
    UserService userService;

    public HistoryController(HistoryService historyService, UserService userService) {
        this.historyService = historyService;
        this.userService = userService;
    }

    @PostMapping("/history/add")
    public int postMatchingHistory(@RequestBody Map<String, Object> map) {
        System.out.println(map.get("userId"));
        int result = -1;
        try {
            int my_idx = userService.getUserIdx((String) map.get("userId"));
            MatchingHistory matchingHistory = new MatchingHistory(my_idx, (Integer) map.get("recommended_idx"));
            result = historyService.postMatchingHistory(matchingHistory);
            System.out.println("insert history result : " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

package org.somemate.demo.matchingHistory.controller;

import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.matchingHistory.service.HistoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/matching")
public class HistoryController {
    HistoryService historyService;
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping("/history/add")
    public int postMatchingHistory(@RequestBody MatchingHistory matchingHistory) {
        System.out.println(matchingHistory.toString());
        int result = -1;
        try {
            result = historyService.postMatchingHistory(matchingHistory);
            System.out.println("insert history result : " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

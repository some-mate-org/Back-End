package org.somemate.demo.matchingHistory.service;

import org.somemate.demo.matchingHistory.dto.MatchingHistory;

import java.sql.SQLException;

public interface HistoryService {
    int postMatchingHistory(MatchingHistory matchingHistory) throws SQLException;
}

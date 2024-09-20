package org.somemate.demo.matchingHistory.service;

import org.somemate.demo.matchingHistory.dao.HistoryDao;
import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class HistoryServiceImp implements HistoryService {
    HistoryDao historyDao;

    HistoryServiceImp(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    @Override
    public int postMatchingHistory(MatchingHistory matchingHistory) throws SQLException {
        return historyDao.postMatchingHistory(matchingHistory);
    }
}

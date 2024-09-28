package org.somemate.demo.matchingHistory.service;

import org.somemate.demo.matchingHistory.dao.HistoryDao;
import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.user.dto.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

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

    @Override
    public ArrayList<User> getUserHistory(int userIdx) throws SQLException {
        return historyDao.getUserHistory(userIdx);
    }
}

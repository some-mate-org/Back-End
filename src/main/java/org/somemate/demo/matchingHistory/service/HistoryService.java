package org.somemate.demo.matchingHistory.service;

import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HistoryService {
    int postMatchingHistory(MatchingHistory matchingHistory) throws SQLException;
    ArrayList<User> getUserHistory(int userIdx) throws SQLException;
<<<<<<< HEAD
    int deleteMatchingHistory(int myIdx, int recommendedIdx) throws SQLException;
=======
>>>>>>> develop
}

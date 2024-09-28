package org.somemate.demo.matchingHistory.dao;

import org.apache.ibatis.annotations.Mapper;
import org.somemate.demo.matchingHistory.dto.MatchingHistory;
import org.somemate.demo.user.dto.User;

import java.sql.SQLException;
import java.util.ArrayList;

@Mapper
public interface HistoryDao {
    public int postMatchingHistory(MatchingHistory matchingHistory) throws SQLException;
    public ArrayList<User> getUserHistory(int userIdx) throws SQLException;
}

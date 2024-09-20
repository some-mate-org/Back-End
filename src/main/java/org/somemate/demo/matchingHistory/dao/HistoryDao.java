package org.somemate.demo.matchingHistory.dao;

import org.apache.ibatis.annotations.Mapper;
import org.somemate.demo.matchingHistory.dto.MatchingHistory;

import java.sql.SQLException;

@Mapper
public interface HistoryDao {
    public int postMatchingHistory(MatchingHistory matchingHistory) throws SQLException;
}

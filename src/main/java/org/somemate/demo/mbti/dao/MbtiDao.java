package org.somemate.demo.mbti.dao;

import org.apache.ibatis.annotations.Mapper;
import org.somemate.demo.mbti.dto.Mbti;

import java.sql.SQLException;

@Mapper
public interface MbtiDao {
    public Mbti getMbtiInfo(String mbti) throws SQLException;
}

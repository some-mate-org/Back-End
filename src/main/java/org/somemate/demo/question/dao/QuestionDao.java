package org.somemate.demo.question.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.somemate.demo.question.dto.Question;

import java.sql.SQLException;

@Mapper
public interface QuestionDao {
    Question getQuestionById(@Param("questionId") int questionId) throws SQLException;
}

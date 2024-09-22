package org.somemate.demo.question.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.somemate.demo.question.dto.Question;

import java.sql.SQLException;
import java.util.ArrayList;

@Mapper
public interface QuestionDao {
    Question getQuestionById(@Param("questionId") int questionId) throws SQLException;
    ArrayList<Question> getEIQuestions() throws SQLException;
    Question getNSQuestion() throws SQLException;
    ArrayList<Question> getFTQuestions() throws SQLException;
    ArrayList<Question> getJPQuestions() throws SQLException;
}

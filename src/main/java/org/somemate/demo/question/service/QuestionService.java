package org.somemate.demo.question.service;

import org.somemate.demo.question.dto.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface QuestionService {
    Question getQuestionById(int questionId) throws SQLException;

    ArrayList<Question> getEIQuestions() throws SQLException;

    Question getNSQuestion() throws SQLException;

    ArrayList<Question> getFTQuestions() throws SQLException;

    ArrayList<Question> getJPQuestions() throws SQLException;
}

package org.somemate.demo.question.service;

import org.somemate.demo.question.dto.Question;

import java.sql.SQLException;

public interface QuestionService {
    Question getQuestionById(int questionId) throws SQLException;
}

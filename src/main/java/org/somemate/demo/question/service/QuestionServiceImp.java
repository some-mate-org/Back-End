package org.somemate.demo.question.service;

import org.somemate.demo.question.dao.QuestionDao;
import org.somemate.demo.question.dto.Question;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class QuestionServiceImp implements QuestionService {
    private final QuestionDao questionDao;

    public QuestionServiceImp(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question getQuestionById(int questionId) throws SQLException {
        return questionDao.getQuestionById(questionId);
    }

}

package org.somemate.demo.question.service;

import org.somemate.demo.question.dao.QuestionDao;
import org.somemate.demo.question.dto.Question;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

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

    @Override
    public ArrayList<Question> getEIQuestions() throws SQLException {
        return questionDao.getEIQuestions();
    }

    @Override
    public Question getNSQuestion() throws SQLException {
        return questionDao.getNSQuestion();
    }

    @Override
    public ArrayList<Question> getFTQuestions() throws SQLException {
        return questionDao.getFTQuestions();
    }

    @Override
    public ArrayList<Question> getJPQuestions() throws SQLException {
        return questionDao.getJPQuestions();
    }

}

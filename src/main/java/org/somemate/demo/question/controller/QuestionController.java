package org.somemate.demo.question.controller;

import org.somemate.demo.question.dto.Question;
import org.somemate.demo.question.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{questionId}")
    public Question getQuestionById(@PathVariable("questionId") int questionId) {
        try {
            return questionService.getQuestionById(questionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            //E-I questions 3개
            questions.addAll(questionService.getEIQuestions());

            //N-S questions 1개
            questions.add(questionService.getNSQuestion());

            //F-T questions 3개
            questions.addAll(questionService.getFTQuestions());

            //J-P questions 3개
            questions.addAll(questionService.getJPQuestions());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

}

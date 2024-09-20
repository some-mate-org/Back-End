package org.somemate.demo.question.controller;

import org.somemate.demo.question.dto.Question;
import org.somemate.demo.question.service.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/questions")
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

}

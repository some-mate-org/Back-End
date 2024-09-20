package org.somemate.demo.question.dto;

public class Question {
    private int idx;
    private String question1_title;
    private String question2_title;
    private String answer1_text;
    private String answer1_value;
    private String answer2_text;
    private String answer2_value;

    public Question(int idx, String question1_title, String question2_title, String answer1_text, String answer1_value, String answer2_text, String answer2_value) {
        this.idx = idx;
        this.question1_title = question1_title;
        this.question2_title = question2_title;
        this.answer1_text = answer1_text;
        this.answer1_value = answer1_value;
        this.answer2_text = answer2_text;
        this.answer2_value = answer2_value;
    }

    // Getters and Setters
    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getQuestion1_title() {
        return question1_title;
    }

    public void setQuestion1_title(String question1_title) {
        this.question1_title = question1_title;
    }

    public String getQuestion2_title() {
        return question2_title;
    }

    public void setQuestion2_title(String question2_title) {
        this.question2_title = question2_title;
    }

    public String getAnswer1_text() {
        return answer1_text;
    }

    public void setAnswer1_text(String answer1_text) {
        this.answer1_text = answer1_text;
    }

    public String getAnswer1_value() {
        return answer1_value;
    }

    public void setAnswer1_value(String answer1_value) {
        this.answer1_value = answer1_value;
    }

    public String getAnswer2_text() {
        return answer2_text;
    }

    public void setAnswer2_text(String answer2_text) {
        this.answer2_text = answer2_text;
    }

    public String getAnswer2_value() {
        return answer2_value;
    }

    public void setAnswer2_value(String answer2_value) {
        this.answer2_value = answer2_value;
    }
}

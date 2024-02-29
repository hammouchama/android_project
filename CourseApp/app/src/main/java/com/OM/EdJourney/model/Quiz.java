package com.OM.EdJourney.model;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    private Long quizId;
    private Chapter chapter;
    private int numberOfQuestions;
    private int timePerQuestion;
    private int requiredScore;
    private List<Question> questions;

    public Quiz(Chapter chapter, int numberOfQuestions, int timePerQuestion, int requiredScore, List<Question> questions) {
        this.chapter = chapter;
        this.numberOfQuestions = numberOfQuestions;
        this.timePerQuestion = timePerQuestion;
        this.requiredScore = requiredScore;
        this.questions = questions;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(int timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(int requiredScore) {
        this.requiredScore = requiredScore;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

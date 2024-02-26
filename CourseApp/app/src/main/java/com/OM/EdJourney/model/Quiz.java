package com.OM.EdJourney.model;

import java.util.List;

public class Quiz {
    private Long quizId;
    private Chapter chapter;
    private int numberOfQuestions;
    private List<Question> questions;

    public Quiz(Chapter chapter, int numberOfQuestions, List<Question> questions) {
        this.chapter = chapter;
        this.numberOfQuestions = numberOfQuestions;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

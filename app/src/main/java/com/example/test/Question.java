package com.example.test;

public class Question {
    private int questionId;
    private boolean trueAnswer;

    public int getQuestionId() {
        return questionId;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public Question(int questionId, boolean trueAnswer) {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }


}

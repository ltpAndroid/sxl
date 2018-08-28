package com.dofun.sxl.bean;

public class Answer extends BaseBean {


    /**
     * topicId : 题目id
     * answerU : 用户答案
     * answer : 正确答案
     * isRight : 1|0
     * score : 分数
     */

    private String topicId;
    private String answerU;
    private String answer;
    private String isRight;
    private String score;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getAnswerU() {
        return answerU;
    }

    public void setAnswerU(String answerU) {
        this.answerU = answerU;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

package com.dofun.sxl.bean;

public class MistakeDetail extends BaseBean {

    /**
     * answer : 草长莺飞二月天
     * answerUser : 草长莺飞三月天
     * createTime : 1535532994000
     * detail : 长,江,飞,月,草,天,三,二,莺
     * homeworkId : 800
     * id : 67
     * num : 0
     * topicId : 677
     * totalScore : 7
     * userId : 2
     */

    private String answer;
    private String answerUser;
    private long createTime;
    private String detail;
    private int homeworkId;
    private int id;
    private int num;
    private int topicId;
    private int totalScore;
    private int userId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

package com.dofun.sxl.bean;

import java.util.List;

public class Statistics extends BaseBean {

    /**
     * answerWrongList : [{"count":9,"fkId":10,"fraction":0},{"count":1,"fkId":11,"fraction":0},{"count":4,"fkId":12,"fraction":0}]
     * averageScore : 30.714
     * fourLevel : 17
     * maxScore : 160
     * minScore : -30
     * oneLevel : 3
     * threeLevel : 1
     * totalCount : 21
     * twoLevel : 0
     */

    private double averageScore;
    private int fourLevel;
    private int maxScore;
    private int minScore;
    private int oneLevel;
    private int threeLevel;
    private int totalCount;
    private int twoLevel;
    private List<AnswerWrongListBean> answerWrongList;

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getFourLevel() {
        return fourLevel;
    }

    public void setFourLevel(int fourLevel) {
        this.fourLevel = fourLevel;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public int getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(int oneLevel) {
        this.oneLevel = oneLevel;
    }

    public int getThreeLevel() {
        return threeLevel;
    }

    public void setThreeLevel(int threeLevel) {
        this.threeLevel = threeLevel;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(int twoLevel) {
        this.twoLevel = twoLevel;
    }

    public List<AnswerWrongListBean> getAnswerWrongList() {
        return answerWrongList;
    }

    public void setAnswerWrongList(List<AnswerWrongListBean> answerWrongList) {
        this.answerWrongList = answerWrongList;
    }

    public static class AnswerWrongListBean {
        /**
         * count : 9
         * fkId : 10
         * fraction : 0
         */

        private int count;
        private int fkId;
        private int fraction;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFkId() {
            return fkId;
        }

        public void setFkId(int fkId) {
            this.fkId = fkId;
        }

        public int getFraction() {
            return fraction;
        }

        public void setFraction(int fraction) {
            this.fraction = fraction;
        }
    }
}

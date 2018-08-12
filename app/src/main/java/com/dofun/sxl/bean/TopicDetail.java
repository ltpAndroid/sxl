package com.dofun.sxl.bean;

import java.util.List;

public class TopicDetail extends BaseBean {


    /**
     * analysis : 线
     * answer : 2639
     * createTime : 1533279265000
     * detail : 慈母手中（ ）,游子身上衣。
     * difficultyDegree : 16
     * fkId : 10
     * fkType : 2
     * fraction : 16
     * id : 564
     * kind : 101
     * optionList : [{"detail":"线","id":2639,"status":0,"topicId":564}]
     * status : 0
     * type : 1
     * userId : 1
     */

    private String analysis;
    private String answer;
    private long createTime;
    private String detail;
    private int difficultyDegree;
    private int fkId;
    private int fkType;
    private int fraction;
    private int id;
    private int kind;
    private int status;
    private int type;
    private int userId;
    private List<OptionListBean> optionList;

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public int getDifficultyDegree() {
        return difficultyDegree;
    }

    public void setDifficultyDegree(int difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
    }

    public int getFkId() {
        return fkId;
    }

    public void setFkId(int fkId) {
        this.fkId = fkId;
    }

    public int getFkType() {
        return fkType;
    }

    public void setFkType(int fkType) {
        this.fkType = fkType;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OptionListBean> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionListBean> optionList) {
        this.optionList = optionList;
    }

    public static class OptionListBean {
        /**
         * detail : 线
         * id : 2639
         * status : 0
         * topicId : 564
         */

        private String detail;
        private int id;
        private int status;
        private int topicId;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }
    }
}

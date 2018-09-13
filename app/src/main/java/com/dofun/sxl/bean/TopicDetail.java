package com.dofun.sxl.bean;

import java.io.Serializable;
import java.util.List;

public class TopicDetail extends BaseBean {


    /**
     * analysis : B.陆游
     * answer : 2714
     * createTime : 1534835227000
     * detail : 1.“山重水复疑无路，柳暗花明又一村。”这句诗是（ ）写的。
     * difficultyDegree : 1
     * fkId : 10
     * fkType : 1
     * fraction : 1
     * id : 623
     * kind : 104
     * lessonId : 376
     * name : 课程名称（小学一年级上学期 ）
     * optionList : [{"detail":"A辛弃疾","id":2713,"status":0,"topicId":623},{"detail":"B.陆游","id":2714,"status":0,"topicId":623},{"detail":"C.杜甫","id":2715,"status":0,"topicId":623}]
     * status : 0
     * type : 1
     * userId : 7
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
    private int lessonId;
    private String name;
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

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static class OptionListBean implements Serializable {
        /**
         * detail : A辛弃疾
         * id : 2713
         * status : 0
         * topicId : 623
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

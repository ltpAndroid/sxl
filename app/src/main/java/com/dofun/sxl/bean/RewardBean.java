package com.dofun.sxl.bean;

public class RewardBean {
    private String rank;
    private int headerId;//没接口，暂时写成这样
    private String name;
    private String praiseNum;
    private String medalNum;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getMedalNum() {
        return medalNum;
    }

    public void setMedalNum(String medalNum) {
        this.medalNum = medalNum;
    }

    public RewardBean(String rank, int headerId, String name, String praiseNum, String medalNum) {
        this.rank = rank;
        this.headerId = headerId;
        this.name = name;
        this.praiseNum = praiseNum;
        this.medalNum = medalNum;
    }
}

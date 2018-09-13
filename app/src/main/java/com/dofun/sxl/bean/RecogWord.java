package com.dofun.sxl.bean;

public class RecogWord extends BaseBean {

    /**
     * evaluate1 : 书写正确规范；字距比较恰当，行款比较整齐。
     * evaluate2 : 单字大小合适；重心比较平稳，字体比较端正。
     * evaluate3 : 结构比例合理；笔画工整。
     * point : -1
     */

    private String evaluate1;
    private String evaluate2;
    private String evaluate3;
    private int point;

    public String getEvaluate1() {
        return evaluate1;
    }

    public void setEvaluate1(String evaluate1) {
        this.evaluate1 = evaluate1;
    }

    public String getEvaluate2() {
        return evaluate2;
    }

    public void setEvaluate2(String evaluate2) {
        this.evaluate2 = evaluate2;
    }

    public String getEvaluate3() {
        return evaluate3;
    }

    public void setEvaluate3(String evaluate3) {
        this.evaluate3 = evaluate3;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

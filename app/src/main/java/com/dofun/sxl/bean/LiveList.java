package com.dofun.sxl.bean;

public class LiveList {
    private String liveTitle;
    private String liveTime;
    private String liveState;
    private String liveDuration;

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getLiveState() {
        return liveState;
    }

    public void setLiveState(String liveState) {
        this.liveState = liveState;
    }

    public String getLiveDuration() {
        return liveDuration;
    }

    public void setLiveDuration(String liveDuration) {
        this.liveDuration = liveDuration;
    }

    public LiveList(String liveTitle, String liveTime, String liveState, String liveDuration) {
        this.liveTitle = liveTitle;
        this.liveTime = liveTime;
        this.liveState = liveState;
        this.liveDuration = liveDuration;
    }
}

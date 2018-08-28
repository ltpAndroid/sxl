package com.dofun.sxl.bean;

public class VideoBean {
    private String videoImg;
    private String videoTime;
    private String videoTitle;
    private String sender;
    private String receiver;
    private String sendTime;

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public VideoBean(String videoTime, String videoTitle, String sender, String receiver, String sendTime) {
        this.videoTime = videoTime;
        this.videoTitle = videoTitle;
        this.sender = sender;
        this.receiver = receiver;
        this.sendTime = sendTime;
    }
}

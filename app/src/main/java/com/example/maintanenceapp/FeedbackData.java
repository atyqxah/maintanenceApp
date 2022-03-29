package com.example.maintanenceapp;

public class FeedbackData {

    private String FeedbackId;
    private String FeedbackComment;
    private String userid;
    private String email;

    public FeedbackData(String FeedbackId, String FeedbackComment, String userid, String email) {
        this.FeedbackId = FeedbackId;
        this.FeedbackComment = FeedbackComment;
        this.userid = userid;
        this.email = email;
    }


    public FeedbackData() {
    }

    public String getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(String FeedbackId) {
        this.FeedbackId = FeedbackId;
    }

    public String getFeedbackComment() {
        return FeedbackComment;
    }

    public void setFeedbackComment(String FeedbackComment) {

        this.FeedbackComment = FeedbackComment;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

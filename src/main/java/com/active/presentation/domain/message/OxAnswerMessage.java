package com.active.presentation.domain.message;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public class OxAnswerMessage {

    private String response;
    private String uid;
    private String userAgent;

    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "OxAnswerMessage{" +
                "response='" + response + '\'' +
                ", uid='" + uid + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
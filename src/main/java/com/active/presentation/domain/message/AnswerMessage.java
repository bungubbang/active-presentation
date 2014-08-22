package com.active.presentation.domain.message;

import com.active.presentation.domain.AudienceType;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public class AnswerMessage {

    private String response;
    private String uid;
    private String userAgent;
    private AudienceType type;
    private String speakerId;

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

    public AudienceType getType() {
        return type;
    }

    public void setType(AudienceType type) {
        this.type = type;
    }

    public String getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
    }

    @Override
    public String toString() {
        return "AnswerMessage{" +
                "response='" + response + '\'' +
                ", uid='" + uid + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", type=" + type +
                ", speakerId='" + speakerId + '\'' +
                '}';
    }
}
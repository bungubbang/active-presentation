package com.active.presentation.ox.domain.message;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public class OxAnswerMessage {

    private String response;
    private String uid;

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

    @Override
    public String toString() {
        return "OxAnswerMessage{" +
                "response='" + response + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}

package com.active.presentation.ox.domain.message;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public class OxErrorAnswerMessage {
    private String code;
    private String message;

    public OxErrorAnswerMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

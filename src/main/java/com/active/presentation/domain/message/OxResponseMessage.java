package com.active.presentation.domain.message;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public class OxResponseMessage {
    private long o;
    private long x;

    private OxAnswerMessage oxAnswerMessage;

    public OxResponseMessage(long o, long x) {
        this.o = o;
        this.x = x;
    }

    public OxResponseMessage(long o, long x, OxAnswerMessage oxAnswerMessage) {
        this.o = o;
        this.x = x;
        this.oxAnswerMessage = oxAnswerMessage;
    }

    public long getO() {
        return o;
    }

    public long getX() {
        return x;
    }

    public OxAnswerMessage getOxAnswerMessage() {
        return oxAnswerMessage;
    }
}
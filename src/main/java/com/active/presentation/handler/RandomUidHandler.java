package com.active.presentation.handler;

import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public class RandomUidHandler implements UidHandler{

    @Override
    public String generateCookieValue() {
        return "w-" + rand_s4() + rand_s4() + "-" + rand_s4() + "-" + rand_s4() + "-" + rand_s4() + "-" + new Date().getTime();
    }

    private String rand_s4() {
        return Long.toHexString((long) ((1 + Math.random()) * 0x10000));
    }
}

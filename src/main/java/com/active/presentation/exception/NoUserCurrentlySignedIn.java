package com.active.presentation.exception;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
public class NoUserCurrentlySignedIn extends RuntimeException {
    public NoUserCurrentlySignedIn(String message) {
        super(message);
    }
}

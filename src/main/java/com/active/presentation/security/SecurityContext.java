package com.active.presentation.security;

import com.active.presentation.domain.Speaker;
import com.active.presentation.exception.NoUserCurrentlySignedIn;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
public class SecurityContext {

    private static final ThreadLocal<Speaker> currentUser = new ThreadLocal<Speaker>();

    public static Speaker getCurrentUser() {
        Speaker user = currentUser.get();
        if (user == null) {
            throw new NoUserCurrentlySignedIn("No user is currently signed in");
        }
        return user;
    }

    public static void setCurrentUser(Speaker user) {
        currentUser.set(user);
    }

    public static boolean userSignedIn() {
        return currentUser.get() != null;
    }

    public static void remove() {
        currentUser.remove();
    }
}

package com.active.presentation.security;

import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
public class SimpleSignInAdapter implements SignInAdapter {

    private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

    @Autowired
    private SpeakerRepository speakerRepository;

    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        Speaker speaker = speakerRepository.findOne(Long.valueOf(userId));
        SecurityContext.setCurrentUser(speaker);
        userCookieGenerator.addCookie(userId, request.getNativeResponse(HttpServletResponse.class));
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        Object board = servletRequest.getSession().getAttribute("board");
        if(board != null) {
            servletRequest.getSession().removeAttribute("board");
            return "/" + board;
        }

        return null;
    }
}

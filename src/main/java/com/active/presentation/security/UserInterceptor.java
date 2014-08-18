package com.active.presentation.security;

import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.SpeakerRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    private final SpeakerRepository speakerRepository;

    private final UsersConnectionRepository connectionRepository;

    private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

    public UserInterceptor(UsersConnectionRepository connectionRepository, SpeakerRepository speakerRepository) {
        this.connectionRepository = connectionRepository;
        this.speakerRepository = speakerRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!isAdminPage(request)) {
            return true;
        }

        rememberUser(request, response);
        if(handleSignOut(request, response)) {
            new RedirectView("/", true).render(null, request, response);
            return false;
        }

        if ((SecurityContext.userSignedIn() && !isDeleteUser()) || requestForSignIn(request)) {
            return true;
        } else {
            return requireSignIn(request, response);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContext.remove();
    }

    // internal helpers

    private void rememberUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = userCookieGenerator.readCookieValue(request);

        if (userId == null) {
            return;
        }
        if (!userNotFound(userId)) {
            userCookieGenerator.removeCookie(response);
            return;
        }

        Speaker speaker = speakerRepository.findOne(Long.valueOf(userId));
        if(speaker == null) {
            userCookieGenerator.removeCookie(response);
            return;
        }

        SecurityContext.setCurrentUser(speaker);
    }

    private boolean handleSignOut(HttpServletRequest request, HttpServletResponse response) {
        if (SecurityContext.userSignedIn() && request.getServletPath().startsWith("/signout")) {
            connectionRepository.createConnectionRepository(String.valueOf(SecurityContext.getCurrentUser().getId())).removeConnections("facebook");
            userCookieGenerator.removeCookie(response);
            SecurityContext.remove();
            return true;
        }
        return false;
    }

    private boolean requestForSignIn(HttpServletRequest request) {
        return request.getServletPath().startsWith("/signin");
    }

    private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        new RedirectView("/signin", true).render(null, request, response);
        return false;
    }

    private boolean userNotFound(String userId) {
        return connectionRepository.createConnectionRepository(userId).findPrimaryConnection(Facebook.class) != null;
    }

    private boolean isAdminPage(HttpServletRequest request) {
        return request.getServletPath().startsWith("/admin")
                || request.getServletPath().startsWith("/sign");
    }

    private boolean isDeleteUser() {
        return !SecurityContext.getCurrentUser().isStatus();
    }

}

package com.active.presentation.controller;

import com.active.presentation.exception.NoUserCurrentlySignedIn;
import com.active.presentation.exception.NotValidUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoUserCurrentlySignedIn.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RedirectView noUserCurrentlySignedIn(Exception e, Locale local) {
        e.printStackTrace();
        return new RedirectView("/signout");
    }

    @ExceptionHandler(NotValidUser.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotValidUser(Exception e, Locale local) {
        e.printStackTrace();
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String defaultException(Exception e, Locale local) {
        e.printStackTrace();
        return "error";
    }
}

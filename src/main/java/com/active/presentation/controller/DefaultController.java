package com.active.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
@Controller
public class DefaultController {

    @RequestMapping("/")
    public RedirectView index() {
        // Landing Page
        return new RedirectView("/admin");
    }

    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping("/signout")
    public String signout() {
        return "signout";
    }
}

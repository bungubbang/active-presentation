package com.active.presentation.ox.controller;

import com.active.presentation.core.handler.UidHandler;
import com.active.presentation.ox.domain.OxDashBoard;
import com.active.presentation.ox.repository.OxDashBoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/9/14
 */
@Controller
public class OxWebController {

    private static final Logger logger = LoggerFactory.getLogger(OxWebController.class);

    @Autowired
    private OxDashBoardRepository oxDashBoardRepository;

    @Autowired
    private UidHandler uidHandler;

    @RequestMapping("/ox")
    public String ox() {
        return "ox";
    }

    @RequestMapping("/ox/dashboard/{id}")
    public String oxDashBoard(@PathVariable Long id, ModelMap map) {
        map.addAttribute("dashboard", oxDashBoardRepository.findOne(id));
        return "ox/ox-dashboard";
    }

    @RequestMapping("/ox/controller/{id}")
    public String oxController(@PathVariable Long id, ModelMap map,
                               @CookieValue(value = "apuid", required = false) String uid,
                               HttpServletResponse response) {
        map.addAttribute("uid", generateApUid(uid, response));
        map.addAttribute("dashboard", oxDashBoardRepository.findOne(id));
        return "ox/ox-controller";
    }

    private String generateApUid(String uid, HttpServletResponse response) {
        if(uid == null) {
            Cookie cookie = new Cookie("apuid", uidHandler.generateCookieValue());
            cookie.setMaxAge(60*60*24*365);
            response.addCookie(cookie);
            return  cookie.getValue();
        }
        return uid;
    }




}

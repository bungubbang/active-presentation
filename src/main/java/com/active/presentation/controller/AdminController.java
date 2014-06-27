package com.active.presentation.controller;

import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.dto.AdminHomeDto;
import com.active.presentation.security.SecurityContext;
import com.active.presentation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/17/14
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private Facebook facebook;

    @Autowired
    public AdminController(Facebook facebook) {
        this.facebook = facebook;
    }

    @Autowired
    private AdminService adminService;

    @ModelAttribute
    public Speaker speaker() {
        return SecurityContext.getCurrentUser();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main(ModelMap map) {
        map.addAttribute("homeData", adminService.getAdminHome(SecurityContext.getCurrentUser()));
        return "admin/main";
    }
}

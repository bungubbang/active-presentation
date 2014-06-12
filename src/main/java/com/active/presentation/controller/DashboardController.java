package com.active.presentation.controller;

import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.repository.PresentationDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
@Controller
@RequestMapping("/board")
public class DashboardController {

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @RequestMapping("/{id}")
    public String board(@PathVariable Long id, ModelMap map) {
        PresentationDashboard board = dashboardRepository.findOne(id);
        map.addAttribute("dashboard", board);

        if(board.getPresentationType().equals(PresentationType.OX)) {
            return "ox/ox-dashboard";
        }
        return "default";
    }


}

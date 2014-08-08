package com.active.presentation.controller;

import com.active.presentation.domain.GroupLists;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.GroupListRepository;
import com.active.presentation.repository.PresentationDashboardRepository;
import com.active.presentation.repository.PresentationDashboardSpecifications;
import com.active.presentation.repository.SpeakerRepository;
import com.active.presentation.security.UserCookieGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private GroupListRepository groupListRepository;

    private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

    @RequestMapping("/{id}")
    public String board(@PathVariable Long id, ModelMap map, HttpServletRequest request) {
        PresentationDashboard board = dashboardRepository.findOne(PresentationDashboardSpecifications.findFetchQuestion(id));
        if(board.getSecure()) {
            if(!isValidSpeaker(board, request)) {
                return "secure";
            }
        }
        map.addAttribute("dashboard", board);

        GroupLists group = groupListRepository.findByDashboardId(id);
        if(group != null) {
            map.addAttribute("left", groupListRepository.findByGroupIdAndListOrder(group.getGroupId(), group.getListOrder() - 1));
            map.addAttribute("right", groupListRepository.findByGroupIdAndListOrder(group.getGroupId(), group.getListOrder() + 1));
        }

        if(board.getPresentationType().equals(PresentationType.OX)) {
            return "ox/ox-dashboard";
        }else if(board.getPresentationType().equals(PresentationType.MULTIPLE_CHOICE)) {
            return "choice/choice-dashboard";
        }else if(board.getPresentationType().equals(PresentationType.QNA)) {
            return "qna/qna-dashboard";
        }
        return "default";
    }

    @RequestMapping("/{id}/tags/{name}")
    public String tags(@PathVariable Long id, @PathVariable String name, ModelMap map, HttpServletRequest request) {
        PresentationDashboard board = dashboardRepository.findOne(PresentationDashboardSpecifications.findFetchQuestion(id));
        if(!board.getPresentationType().equals(PresentationType.QNA)) {
            return "redirect:/board/" + id;
        }
        if(board.getSecure()) {
            if(!isValidSpeaker(board, request)) {
                return "secure";
            }
        }
        map.addAttribute("dashboard", board);
        map.addAttribute("tag", name);
        return "qna/qna-tags";
    }

    private boolean isValidSpeaker(PresentationDashboard dashboard, HttpServletRequest request) {
        String userId = userCookieGenerator.readCookieValue(request);

        if (userId == null || userId.isEmpty()) {
            return false;
        }

        Speaker speaker = speakerRepository.findOne(Long.valueOf(userId));
        if(dashboard.getSpeaker() != speaker) {
            return false;
        }

        return true;
    }



}

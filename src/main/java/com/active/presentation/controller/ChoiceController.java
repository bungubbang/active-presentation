package com.active.presentation.controller;

import com.active.presentation.domain.*;
import com.active.presentation.handler.UidHandler;
import com.active.presentation.repository.*;
import com.active.presentation.security.UserCookieGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
 * Date: 6/11/14
 */
@Controller
public class ChoiceController {

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private GroupListRepository groupListRepository;

    @Autowired
    private UidHandler uidHandler;

    @Autowired
    private SpeakerRepository speakerRepository;

    private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

    @RequestMapping("/{id}")
    public String oxController(@PathVariable Long id, ModelMap map,
                               @CookieValue(value = "apuid", required = false) String apuid,
                               HttpServletRequest request, HttpServletResponse response) {
        String uid = generateApUid(apuid, response);
        Audience audience = audienceRepository.findByUserKey(uid);
        PresentationDashboard dashboard = dashboardRepository.findOne(PresentationDashboardSpecifications.findFetchQuestion(id));

        GroupLists group = groupListRepository.findByDashboardId(id);
        if(group != null) {
            map.addAttribute("left", groupListRepository.findByGroupIdAndListOrder(group.getGroupId(), group.getListOrder() - 1));
            map.addAttribute("right", groupListRepository.findByGroupIdAndListOrder(group.getGroupId(), group.getListOrder() + 1));
        }

        if(!dashboard.getAnonymous()) {
            String userId = userCookieGenerator.readCookieValue(request);
            if(!userId.isEmpty()) {
                map.addAttribute("speaker", speakerRepository.findOne(Long.valueOf(userId)));
            }
        }

        map.addAttribute("uid", uid);
        map.addAttribute("dashboard", dashboard);

        return checkPresentationType(dashboard, audience, map);
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

    private String checkPresentationType(PresentationDashboard dashboard, Audience audience, ModelMap map) {
        if(dashboard.getPresentationType().equals(PresentationType.OX)) {
            attachAnswer(dashboard, audience, map);
            return "ox/ox-controller";
        }else if(dashboard.getPresentationType().equals(PresentationType.MULTIPLE_CHOICE)) {
            attachAnswer(dashboard, audience, map);
            return "choice/choice-controller";
        }else if(dashboard.getPresentationType().equals(PresentationType.QNA)) {
            return "qna/qna-controller";
        }
        return "default";
    }

    private void attachAnswer(PresentationDashboard dashboard, Audience audience, ModelMap map) {
        Answer answer = answerRepository.findByDashboardAndAudience(dashboard, audience);
        if(answer != null) {
            if(dashboard.getPresentationType().equals(PresentationType.OX)) {
                map.addAttribute("answer", answer.getResult());
            } else if(dashboard.getPresentationType().equals(PresentationType.MULTIPLE_CHOICE)) {
                map.addAttribute("answer", answer.getResultId());
            }
        }
    }

}

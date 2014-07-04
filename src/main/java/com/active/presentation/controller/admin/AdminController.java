package com.active.presentation.controller.admin;

import com.active.presentation.controller.admin.form.BoardModifyForm;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Question;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.PresentationDashboardRepository;
import com.active.presentation.repository.QuestionRepository;
import com.active.presentation.repository.dto.AdminHomeDto;
import com.active.presentation.security.SecurityContext;
import com.active.presentation.service.AdminService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.active.presentation.domain.PresentationType.*;

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

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @ModelAttribute
    public Speaker speaker() {
        return SecurityContext.getCurrentUser();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main(ModelMap map) {
        map.addAttribute("homeData", adminService.getAdminHome(SecurityContext.getCurrentUser()));
        return "admin/main";
    }

    @RequestMapping(value = "/ox", method = RequestMethod.GET)
    public String ox(ModelMap map) {
        map.addAttribute("oxData", dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), OX));
        return "admin/ox";
    }

    @RequestMapping(value = "/make/ox", method = RequestMethod.GET)
    public String makeOxPage(PresentationDashboard dashboard, ModelMap map) {
        dashboard.setPresentationType(PresentationType.OX);
        map.addAttribute("data", dashboard);
        return "admin/make";

    }

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public String makeOx(PresentationDashboard dashboard) {
        dashboard.setSpeaker(SecurityContext.getCurrentUser());

        if(dashboard.getPresentationType().equals(PresentationType.OX)) {
            List<Question> questions = Lists.newArrayList(
                    questionRepository.save(new Question("O")), questionRepository.save(new Question("X")));
            dashboard.setQuestions(questions);
        }
        dashboardRepository.save(dashboard);
        return "redirect:/admin/ox";
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modifyPage(ModelMap map, @PathVariable Long id) {
        map.addAttribute("data", dashboardRepository.findOne(id));
        return "admin/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(BoardModifyForm board) {

        PresentationDashboard dashboard = dashboardRepository.findOne(board.getId());
        dashboard.setTitle(board.getTitle());
        dashboard.setSecure(board.isSecure());
        dashboard.setStatus(board.isStatus());

        if(board.getQuestions() != null) {
            // TODO questions Update!
        }

        dashboardRepository.save(dashboard);

        switch (dashboard.getPresentationType()) {
            case OX:
                return "redirect:/admin/ox";
            case MULTIPLE_CHOICE:
                return "redirect:/admin/multiple";
            case QNA:
                return "redirect:/admin/qna";
        }
        return "redirect:/admin";
    }
}

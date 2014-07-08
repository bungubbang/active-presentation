package com.active.presentation.controller.admin;

import com.active.presentation.controller.admin.form.BoardMakeForm;
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
import org.springframework.data.domain.Sort;
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
        map.addAttribute("datas", dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), OX, new Sort(Sort.Direction.DESC, "createdDate")));
        return "admin/board";
    }

    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    public String multiChoice(ModelMap map) {
        map.addAttribute("datas", dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), MULTIPLE_CHOICE, new Sort(Sort.Direction.DESC, "createdDate")));
        return "admin/board";
    }

    @RequestMapping(value = "/qna", method = RequestMethod.GET)
    public String qna(ModelMap map) {
        map.addAttribute("datas", dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), QNA, new Sort(Sort.Direction.DESC, "createdDate")));
        return "admin/board";
    }

    @RequestMapping(value = "/make/ox", method = RequestMethod.GET)
    public String makeOxPage(PresentationDashboard dashboard, ModelMap map) {
        dashboard.setPresentationType(PresentationType.OX);
        map.addAttribute("data", dashboard);
        return "admin/make";

    }

    @RequestMapping(value = "/make/multi", method = RequestMethod.GET)
    public String makeMultiPage(PresentationDashboard dashboard, ModelMap map) {
        dashboard.setPresentationType(PresentationType.MULTIPLE_CHOICE);
        map.addAttribute("data", dashboard);
        return "admin/make";
    }

    @RequestMapping(value = "/make/qna", method = RequestMethod.GET)
    public String makeQnaPage(PresentationDashboard dashboard, ModelMap map) {
        dashboard.setPresentationType(PresentationType.QNA);
        map.addAttribute("data", dashboard);
        return "admin/make";
    }

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public String makeOx(BoardMakeForm makeForm) {
        adminService.addBoard(new PresentationDashboard(makeForm.getPresentationType(), makeForm.getTitle(), makeForm.getStatus(), makeForm.getSecure())
                            , makeForm.getQuestionList());
        return typeReturn(makeForm.getPresentationType());
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modifyPage(ModelMap map, @PathVariable Long id) {
        map.addAttribute("data", dashboardRepository.findOne(id));
        return "admin/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(BoardModifyForm board) {
        PresentationDashboard dashboard = adminService.modifyBoard(board);
        return typeReturn(dashboard.getPresentationType());
    }

    private String typeReturn(PresentationType type) {
        switch (type) {
            case OX:
                return "redirect:/admin/ox";
            case MULTIPLE_CHOICE:
                return "redirect:/admin/multi";
            case QNA:
                return "redirect:/admin/qna";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        dashboardRepository.delete(id);
        return "redirect:/admin";
    }
}

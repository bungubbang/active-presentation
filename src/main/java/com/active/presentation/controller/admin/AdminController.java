package com.active.presentation.controller.admin;

import com.active.presentation.controller.admin.form.BoardMakeForm;
import com.active.presentation.controller.admin.form.BoardModifyForm;
import com.active.presentation.controller.admin.form.GroupForm;
import com.active.presentation.domain.DashboardGroup;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.*;
import com.active.presentation.security.SecurityContext;
import com.active.presentation.security.UserCookieGenerator;
import com.active.presentation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private AdminService adminService;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private DashboardGroupRepository groupRepository;

    @Autowired
    private GroupListRepository groupListRepository;

    private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

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
        List<PresentationDashboard> dashboards = dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), OX, new Sort(Sort.Direction.DESC, "createdDate"));
        if(dashboards == null) {
            return "redirect:/admin/make/ox";
        }

        map.addAttribute("datas", dashboards);
        map.addAttribute("boardType", "OX");
        return "admin/board";
    }

    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    public String multiChoice(ModelMap map) {
        List<PresentationDashboard> dashboards = dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), MULTIPLE_CHOICE, new Sort(Sort.Direction.DESC, "createdDate"));
        if(dashboards == null) {
            return "redirect:/admin/make/multi";
        }
        map.addAttribute("datas", dashboards);
        map.addAttribute("boardType", "MULTIPLE");
        return "admin/board";
    }

    @RequestMapping(value = "/qna", method = RequestMethod.GET)
    public String qna(ModelMap map) {
        List<PresentationDashboard> dashboards = dashboardRepository.findBySpeakerAndPresentationType(SecurityContext.getCurrentUser(), QNA, new Sort(Sort.Direction.DESC, "createdDate"));
        if(dashboards == null) {
            return "redirect:/admin/make/qna";
        }
        map.addAttribute("datas", dashboards);
        map.addAttribute("boardType", "QNA");
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
    public String makeOx(BoardMakeForm makeForm, ModelMap map) {
        if (checkNullQuestions(makeForm, map)) return "admin/make";
        adminService.addBoard(new PresentationDashboard(makeForm.getPresentationType(), makeForm.getTitle(), makeForm.getStatus(), makeForm.getSecure(), makeForm.getAnonymous())
                            , makeForm.getQuestionList());
        return typeReturn(makeForm.getPresentationType());
    }

    private boolean checkNullQuestions(BoardMakeForm makeForm, ModelMap map) {
        if(makeForm.getPresentationType().equals(MULTIPLE_CHOICE) && makeForm.getQuestionList().trim().length() == 0 ) {
            PresentationDashboard dashboard = new PresentationDashboard(PresentationType.MULTIPLE_CHOICE, makeForm.getTitle(), makeForm.getStatus(), makeForm.getSecure(), makeForm.getAnonymous());
            map.addAttribute("data", dashboard);
            map.addAttribute("error", "questions");
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modifyPage(ModelMap map, @PathVariable Long id) {
        PresentationDashboard dashboard = dashboardRepository.findOne(id);
        if(dashboard == null || !dashboard.getSpeaker().equals(SecurityContext.getCurrentUser())) {
            return "redirect:/";
        }
        map.addAttribute("data", dashboard);
        return "admin/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(BoardModifyForm board) {
        PresentationDashboard dashboard = adminService.modifyBoard(board);
        return typeReturn(dashboard.getPresentationType());
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String accountPage() {
        return "admin/account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.PUT)
    public String accountModify(Speaker speaker) {
        if(SecurityContext.getCurrentUser() == speaker) {
            speakerRepository.save(speaker);
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    public String accountModify(@PathVariable Long id, HttpServletResponse response) {
        Speaker speaker = speakerRepository.findOne(id);
        if(SecurityContext.getCurrentUser().equals(speaker)) {
            adminService.deleteUser(id);
            userCookieGenerator.removeCookie(response);
            SecurityContext.remove();
        }
        return "redirect:/";
    }

    @RequestMapping("/landing")
    public String landing() {
        return "landing";
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
        PresentationDashboard dashboard = dashboardRepository.findOne(id);
        if(dashboard == null || !dashboard.getSpeaker().equals(SecurityContext.getCurrentUser())) {
            return "redirect:/";
        }
        dashboardRepository.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String groupListPage(ModelMap map) {
        List<DashboardGroup> groups = groupRepository.findBySpeaker(SecurityContext.getCurrentUser());
        if(groups == null) {
            return "redirect:/admin/make/group";
        }

        map.addAttribute("datas", groups);
        return "admin/group";
    }

    @RequestMapping(value = "/group/make", method = RequestMethod.GET)
    public String groupMakePage(DashboardGroup dashboardGroup, ModelMap map){
        map.addAttribute("data", dashboardGroup);
        map.addAttribute("boards", adminService.notSelectedBoards());
        return "admin/makeGroup";
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
    public String groupModifyPage(@PathVariable Long id, ModelMap map){
        map.addAttribute("data", groupRepository.findOne(id));
        map.addAttribute("boards", adminService.notSelectedBoards());
        map.addAttribute("groups", adminService.selectedBoards(id));
        return "admin/modifyGroup";
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public String groupMake(GroupForm form) {
        adminService.addGroup(form);
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public String modifyGroup(GroupForm form) {
        adminService.modifyGroup(form);
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    public String deleteGroup(@PathVariable Long id) {
        adminService.deleteGroup(id);
        return "redirect:/admin/group";
    }
}

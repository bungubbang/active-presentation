package com.active.presentation.controller.admin;

import com.active.presentation.controller.admin.form.BoardResultForm;
import com.active.presentation.controller.admin.form.BoardStatusForm;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.exception.NotValidUser;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.repository.PresentationDashboardRepository;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.active.presentation.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/4/14
 */
@RestController
@RequestMapping("/admin/api")
public class AdminApiController {

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public PresentationDashboard modifyStatus(@Valid BoardStatusForm statusForm) {
        PresentationDashboard dashboard = dashboardRepository.findOne(statusForm.getId());
        checkValidUser(dashboard);
        dashboard.setStatus(statusForm.getStatus());
        return dashboardRepository.save(dashboard);
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public BoardResultForm countResult(Long id) {
        BoardResultForm resultForm = new BoardResultForm();
        resultForm.setDashboardId(id);
        resultForm.setResults(answerRepository.resultByDashboard(dashboardRepository.findOne(id)));
        return resultForm;
    }

    private void checkValidUser(PresentationDashboard dashboard) {
        if(!dashboard.getSpeaker().getId().equals(SecurityContext.getCurrentUser().getId())) {
            throw new NotValidUser();
        }
    }
}

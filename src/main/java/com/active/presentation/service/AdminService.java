package com.active.presentation.service;

import com.active.presentation.controller.admin.form.BoardModifyForm;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.dto.AdminHomeDto;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/27/14
 */
public interface AdminService {
    AdminHomeDto getAdminHome(Speaker speaker);
    PresentationDashboard addBoard(PresentationDashboard dashboard, String questionList);
    PresentationDashboard modifyBoard(BoardModifyForm boardModifyForm);
    void deleteUser(Long id);
}

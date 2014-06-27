package com.active.presentation.service;

import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.dto.AdminHomeDto;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/27/14
 */
public interface AdminService {
    AdminHomeDto getAdminHome(Speaker speaker);
}

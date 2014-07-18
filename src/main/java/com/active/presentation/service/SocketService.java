package com.active.presentation.service;

import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.message.SocketResponseMessage;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
public interface SocketService {
    Audience generateAudience(String uid);
    PresentationDashboard findOxDashBoard(Long id);
    SocketResponseMessage checkSecure(PresentationDashboard dashboard);
}

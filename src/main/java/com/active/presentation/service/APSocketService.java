package com.active.presentation.service;

import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.AudienceRepository;
import com.active.presentation.repository.PresentationDashboardRepository;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
@Service
public class APSocketService implements SocketService {

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    public Audience generateAudience(String uid) {
        Audience audience = audienceRepository.findByUserKey(uid);
        if(audience == null) {
            audience = audienceRepository.save(new Audience(uid));
        }
        return audience;
    }

    public PresentationDashboard findOxDashBoard(Long id) {
        PresentationDashboard dashboard = dashboardRepository.findOne(id);
        if(dashboard == null) {
            throw new NullPointerException();
        }
        return dashboard;
    }

    @Override
    public SocketResponseMessage checkSecure(PresentationDashboard dashboard) {
        return new SocketResponseMessage(Lists.newArrayList(new AnswerResultDto(null, null, dashboard.getStatus())));
    }
}

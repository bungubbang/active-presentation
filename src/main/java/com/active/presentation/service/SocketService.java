package com.active.presentation.service;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.Tag;
import com.active.presentation.domain.message.AnswerMessage;
import com.active.presentation.domain.message.SocketResponseMessage;

import java.util.Set;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
public interface SocketService {
    Audience generateAudience(AnswerMessage message);
    PresentationDashboard findOxDashBoard(Long id);
    SocketResponseMessage checkSecure(PresentationDashboard dashboard);
    Set<Tag> parsingTags(String message);
}

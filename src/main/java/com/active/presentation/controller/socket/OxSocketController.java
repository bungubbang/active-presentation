package com.active.presentation.controller.socket;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.message.OxAnswerMessage;
import com.active.presentation.domain.message.OxResponseMessage;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.repository.AudienceRepository;
import com.active.presentation.repository.PresentationDashboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Controller
public class OxSocketController {

    private static final Logger logger = LoggerFactory.getLogger(OxSocketController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @SubscribeMapping("/players/answer/ox/{boardId}")
    public OxResponseMessage answerResponse(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = findOxDashBoard(boardId);
        return new OxResponseMessage(answerRepository.countByDashboardAndResult(dashboard, "o"),
                answerRepository.countByDashboardAndResult(dashboard, "x"));
    }

    @MessageMapping("/answer/ox/{boardId}")
    public void answer(@DestinationVariable Long boardId, OxAnswerMessage message) {
        Audience audience = generateAudience(message.getUid());
        PresentationDashboard dashboard = findOxDashBoard(boardId);
        Answer answer = answerRepository.findByDashboardAndAudience(dashboard, audience);
        if(answer != null) {
            answer.setResult(message.getResponse());
            answer.setModifyDate(new Date());
            answerRepository.save(answer);
        } else {
            answerRepository.save(new Answer(dashboard, audience, message.getResponse()));
        }

        OxResponseMessage oxResponseMessage = new OxResponseMessage(answerRepository.countByDashboardAndResult(dashboard, "o"),
                                                                    answerRepository.countByDashboardAndResult(dashboard, "x"), message);
        messagingTemplate.convertAndSend("/socket/players/answer/ox/" + dashboard.getId(), oxResponseMessage);
    }

    private Audience generateAudience(String uid) {
        Audience audience = audienceRepository.findByUserKey(uid);
        if(audience == null) {
            audience = audienceRepository.save(new Audience(uid));
        }
        return audience;
    }

    private PresentationDashboard findOxDashBoard(Long id) {
        PresentationDashboard dashboard = dashboardRepository.findOne(id);
        if(dashboard == null) {
            throw new NullPointerException();
        }
        return dashboard;
    }


}

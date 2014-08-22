package com.active.presentation.controller.socket;

import com.active.presentation.domain.*;
import com.active.presentation.domain.message.AnswerAudienceListMessage;
import com.active.presentation.domain.message.AnswerMessage;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.repository.QuestionRepository;
import com.active.presentation.repository.SpeakerRepository;
import com.active.presentation.service.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

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
    private QuestionRepository questionRepository;

    @Autowired
    private SocketService socketService;

    @Autowired
    private SpeakerRepository speakerRepository;

    @SubscribeMapping("/players/answer/ox/{boardId}")
    public SocketResponseMessage answerResponse(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        return new SocketResponseMessage(answerRepository.resultByDashboard(dashboard));
    }

    @SubscribeMapping("/players/answer/ox/audience/{boardId}")
    public List<AnswerAudienceListMessage> audience(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        return answerRepository.findByAnswerAudienceList(dashboard, false);

    }

    @MessageMapping("/answer/ox/{boardId}")
    public void answer(@DestinationVariable Long boardId, AnswerMessage message) {
        Audience audience = socketService.generateAudience(message);
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        Answer answer = answerRepository.findByDashboardAndAudience(dashboard, audience);
        Question question = questionRepository.searchBoardAndAnswer(dashboard.getId(), message.getResponse());
        if(answer != null) {
            answer.setResultId(question.getId());
            answer.setResult(message.getResponse());
            answer.setModifyDate(new Date());
        } else {
            answer = new Answer(dashboard, audience, question.getId(), message.getResponse(), message.getUserAgent());
        }
        if(message.getType().equals(AudienceType.FACEBOOK)) {
            Speaker speaker = speakerRepository.findOne(Long.valueOf(message.getSpeakerId()));
            answer.setName(speaker.getName());
            answer.setProfileImage(speaker.getProfileImage());
            answer.setAnonymous(false);
        } else {
            answer.setName(null);
            answer.setProfileImage(null);
            answer.setAnonymous(true);
        }
        answerRepository.save(answer);

        SocketResponseMessage socketResponseMessage = new SocketResponseMessage(answerRepository.resultByDashboard(dashboard), message.getResponse());
        messagingTemplate.convertAndSend("/socket/players/answer/ox/" + dashboard.getId(), socketResponseMessage);

        List<AnswerAudienceListMessage> audienceList = answerRepository.findByAnswerAudienceList(dashboard, false);
        messagingTemplate.convertAndSend("/socket/players/answer/audience/" + dashboard.getId(), audienceList);
    }
}

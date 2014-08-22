package com.active.presentation.controller.socket;

import com.active.presentation.domain.*;
import com.active.presentation.domain.message.AnswerMessage;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.*;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.active.presentation.service.SocketService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/16/14
 */
@Controller
public class QnaSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SocketService socketService;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @SubscribeMapping("/players/answer/qna/{boardId}")
    public SocketResponseMessage answerResponse(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        if(dashboard.getSecure()) {
            return socketService.checkSecure(dashboard);
        }
        return new SocketResponseMessage(answerRepository.findByDashboardOnAnswerResultDto(dashboard));
    }

    @MessageMapping("/answer/qna/{boardId}")
    public void answer(@DestinationVariable Long boardId, AnswerMessage message) {
        Audience audience = socketService.generateAudience(message);
        PresentationDashboard dashboard = dashboardRepository.findOne(boardId);
        Question question = questionRepository.searchBoardAndAnswer(dashboard.getId(), "Q");
        Answer answer = new Answer(dashboard, audience, question.getId(), message.getResponse(), message.getUserAgent());

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

        Set<Tag> tags = socketService.parsingTags(message.getResponse());

        answer.setTags(tags);
        answerRepository.save(answer);

        Set<Tag> dashboardTags;
        PresentationDashboard one = dashboardRepository.findOne(PresentationDashboardSpecifications.findFetchTags(boardId));
        if(one == null) {
            dashboard.setTags(tags);
        } else {
            dashboardTags = one.getTags();
            dashboardTags.addAll(tags);
            dashboard.setTags(dashboardTags);
        }

        dashboardRepository.save(dashboard);

        SocketResponseMessage responseMessage =
                new SocketResponseMessage(Lists.newArrayList(new AnswerResultDto(question.getId(), message.getResponse(), new Date(), dashboard.getStatus(), answer.getAnonymous(), answer.getName(), answer.getProfileImage())));

        messagingTemplate.convertAndSend("/socket/players/answer/qna/result/" + dashboard.getId(), responseMessage);
        messagingTemplate.convertAndSend("/socket/players/answer/qna/" + dashboard.getId() + "/taglist", dashboard.getTags());
    }

    @SubscribeMapping("/players/answer/qna/{boardId}/tag/{message}")
    public SocketResponseMessage answerTagResponse(@DestinationVariable Long boardId, @DestinationVariable String message) throws UnsupportedEncodingException {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        if(dashboard.getSecure()) {
            return socketService.checkSecure(dashboard);
        }
        message = URLDecoder.decode(message, "utf-8");
        return new SocketResponseMessage(answerRepository.findByTagsName(message));
    }

    @SubscribeMapping("/players/answer/qna/{boardId}/taglist")
    public Set<Tag> answerTagLists(@DestinationVariable Long boardId) throws UnsupportedEncodingException {
        PresentationDashboard dashboard = dashboardRepository.findOne(PresentationDashboardSpecifications.findFetchTags(boardId));
        if(dashboard != null) {
            return dashboard.getTags();
        }
        return null;

    }
}

package com.active.presentation.controller.socket;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.message.AnswerMessage;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.active.presentation.service.SocketService;
import com.google.common.collect.Lists;
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
 * Date: 6/16/14
 */
@Controller
public class QnaSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SocketService socketService;

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
        Audience audience = socketService.generateAudience(message.getUid());
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        answerRepository.save(new Answer(dashboard, audience, message.getResponse(), message.getUserAgent()));

        SocketResponseMessage responseMessage =
                new SocketResponseMessage(Lists.newArrayList(new AnswerResultDto(message.getResponse(), new Date(), dashboard.getStatus())));

        messagingTemplate.convertAndSend("/socket/players/answer/qna/result/" + dashboard.getId(), responseMessage);
    }
}

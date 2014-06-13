package com.active.presentation.controller.socket;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.message.OxAnswerMessage;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.service.SocketService;
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
 * Date: 6/13/14
 */
@Controller
public class ChoiceSocketController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SocketService socketService;

    @SubscribeMapping("/players/answer/choice/{boardId}")
    public SocketResponseMessage answerResponse(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        return new SocketResponseMessage(answerRepository.resultByDashboard(dashboard));
    }

    @MessageMapping("/answer/choice/{boardId}")
    public void answer(@DestinationVariable Long boardId, OxAnswerMessage message) {
        Audience audience = socketService.generateAudience(message.getUid());
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        Answer answer = answerRepository.findByDashboardAndAudience(dashboard, audience);
        if(answer != null) {
            answer.setResult(message.getResponse());
            answer.setModifyDate(new Date());
            answerRepository.save(answer);
        } else {
            answerRepository.save(new Answer(dashboard, audience, message.getResponse(), message.getUserAgent()));
        }

        SocketResponseMessage socketResponseMessage = new SocketResponseMessage(answerRepository.resultByDashboard(dashboard), message.getResponse());
        messagingTemplate.convertAndSend("/socket/players/answer/choice/" + dashboard.getId(), socketResponseMessage);
    }
}

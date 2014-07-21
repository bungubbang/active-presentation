package com.active.presentation.controller.socket;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.Question;
import com.active.presentation.domain.message.AnswerMessage;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.repository.QuestionRepository;
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

    @SubscribeMapping("/players/answer/ox/{boardId}")
    public SocketResponseMessage answerResponse(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        return new SocketResponseMessage(answerRepository.resultByDashboard(dashboard));
    }

    @MessageMapping("/answer/ox/{boardId}")
    public void answer(@DestinationVariable Long boardId, AnswerMessage message) {
        Audience audience = socketService.generateAudience(message.getUid());
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        Answer answer = answerRepository.findByDashboardAndAudience(dashboard, audience);
        Question question = questionRepository.searchBoardAndId(dashboard.getId(), Long.valueOf(message.getResponse()));
        if(answer != null) {
            answer.setResultId(question.getId());
            answer.setResult(message.getResponse());
            answer.setModifyDate(new Date());
            answerRepository.save(answer);
        } else {
            answerRepository.save(new Answer(dashboard, audience, question.getId(), message.getResponse(), message.getUserAgent()));
        }

        SocketResponseMessage socketResponseMessage = new SocketResponseMessage(answerRepository.resultByDashboard(dashboard), message.getResponse());
        messagingTemplate.convertAndSend("/socket/players/answer/ox/" + dashboard.getId(), socketResponseMessage);
    }


}

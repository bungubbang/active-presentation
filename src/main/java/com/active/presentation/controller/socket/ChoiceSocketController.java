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
    private QuestionRepository questionRepository;

    @Autowired
    private SocketService socketService;

    @SubscribeMapping("/players/answer/choice/{boardId}")
    public SocketResponseMessage answerResponse(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        return new SocketResponseMessage(answerRepository.resultByDashboard(dashboard));
    }

    @MessageMapping("/answer/choice/{boardId}")
    public void answer(@DestinationVariable Long boardId, AnswerMessage message) {
        Audience audience = socketService.generateAudience(message.getUid());
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        Answer answer = answerRepository.findByDashboardAndAudience(dashboard, audience);
        Question question = questionRepository.searchBoardAndId(dashboard.getId(), Long.valueOf(message.getResponse()));
        if(answer != null) {
            answer.setResultId(question.getId());
            answer.setResult(question.getAnswerList());
            answer.setModifyDate(new Date());
            answerRepository.save(answer);
        } else {
            answerRepository.save(new Answer(dashboard, audience, question.getId(), question.getAnswerList(), message.getUserAgent()));
        }

        SocketResponseMessage socketResponseMessage = new SocketResponseMessage(answerRepository.resultByDashboard(dashboard), message.getResponse());
        messagingTemplate.convertAndSend("/socket/players/answer/choice/" + dashboard.getId(), socketResponseMessage);
    }
}
